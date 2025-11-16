import torch
import torch.nn as nn
import torch.optim as optim
from torch.utils.data import DataLoader
from torchvision import datasets, models, transforms
import matplotlib.pyplot as plt
import os
import copy

# --- 1. Definirea Parametrilor ---
IMG_WIDTH, IMG_HEIGHT = 224, 224
BATCH_SIZE = 16
EPOCHS = 15
DATA_DIR = 'data'
MODEL_PATH = 'clean_dirty_classifier.pth' # Noul nume al modelului

device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
print(f"Se va folosi dispozitivul: {device}")

# --- 2. Pregătirea Datelor (Data Augmentation și Loadere) ---
data_transforms = {
    # 'train': transforms.Compose([
    #     transforms.RandomResizedCrop(IMG_WIDTH),
    #     transforms.RandomHorizontalFlip(),
    #     transforms.RandomRotation(30),
    #     transforms.ToTensor(),
    #     transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])
    # ]),
    'train': transforms.Compose([
        transforms.RandomResizedCrop(IMG_WIDTH),
        transforms.RandomHorizontalFlip(),
        transforms.RandomRotation(30),
        # --- AICI E MODIFICAREA ---
        # Adaugă această linie pentru a simula luminozitate și contrast diferit:
        transforms.ColorJitter(brightness=0.2, contrast=0.2, saturation=0.2),
        # -------------------------
        transforms.ToTensor(),
        transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])
    ]),
    'validation': transforms.Compose([
        transforms.Resize(256),
        transforms.CenterCrop(IMG_WIDTH),
        transforms.ToTensor(),
        transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])
    ]),
}

image_datasets = {x: datasets.ImageFolder(os.path.join(DATA_DIR, x), data_transforms[x])
                  for x in ['train', 'validation']}

dataloaders = {x: DataLoader(image_datasets[x], batch_size=BATCH_SIZE, shuffle=True)
               for x in ['train', 'validation']}

dataset_sizes = {x: len(image_datasets[x]) for x in ['train', 'validation']}
class_names = image_datasets['train'].classes

print(f"Clasele gasite: {class_names}")
print(f"Folosim {dataset_sizes['train']} imagini pt antrenare, {dataset_sizes['validation']} pt validare.")

# --- 3. Construirea Modelului (Transfer Learning) ---
model = models.mobilenet_v2(weights=models.MobileNet_V2_Weights.IMAGENET1K_V1)

# Îngheață toate straturile de bază
for param in model.parameters():
    param.requires_grad = False

in_features = model.classifier[1].in_features
model.classifier = nn.Sequential(
    nn.Dropout(p=0.2),
    nn.Linear(in_features, 1) # O singură ieșire pentru clasificare binară
)
model = model.to(device)

# --- 4. Definirea Funcției de Pierdere și a Optimizatorului ---
criterion = nn.BCEWithLogitsLoss() # Combină Sigmoid + Binary Cross Entropy
optimizer = optim.Adam(model.classifier.parameters(), lr=0.001)

# --- 5. Bucla de Antrenare ---
print("\nÎncepe antrenarea...")
best_model_wts = copy.deepcopy(model.state_dict())
best_acc = 0.0
history = {'train_loss': [], 'train_acc': [], 'val_loss': [], 'val_acc': []}

for epoch in range(EPOCHS):
    print(f'Epoca {epoch + 1}/{EPOCHS}')
    print('-' * 10)

    for phase in ['train', 'validation']:
        if phase == 'train':
            model.train()
        else:
            model.eval()

        running_loss = 0.0
        running_corrects = 0

        for inputs, labels in dataloaders[phase]:
            inputs = inputs.to(device)
            labels = labels.to(device).float().view(-1, 1) # Formatare corectă

            optimizer.zero_grad()

            with torch.set_grad_enabled(phase == 'train'):
                outputs = model(inputs)
                loss = criterion(outputs, labels)
                preds = torch.sigmoid(outputs) > 0.5

                if phase == 'train':
                    loss.backward()
                    optimizer.step()

            running_loss += loss.item() * inputs.size(0)
            running_corrects += torch.sum(preds == labels.data)

        epoch_loss = running_loss / dataset_sizes[phase]
        epoch_acc = running_corrects.double() / dataset_sizes[phase]

        print(f'{phase} Loss: {epoch_loss:.4f} Acc: {epoch_acc:.4f}')

        if phase == 'train':
            history['train_loss'].append(epoch_loss)
            history['train_acc'].append(epoch_acc.item())
        else:
            history['val_loss'].append(epoch_loss)
            history['val_acc'].append(epoch_acc.item())

        if phase == 'validation' and epoch_acc > best_acc:
            best_acc = epoch_acc
            best_model_wts = copy.deepcopy(model.state_dict())
    print()

print(f'Antrenare finalizată. Cea mai bună acuratețe (validare): {best_acc:.4f}')
model.load_state_dict(best_model_wts)

# --- 6. Salvarea Modelului ---
torch.save(model.state_dict(), MODEL_PATH)
print(f"Modelul a fost salvat ca '{MODEL_PATH}'")

# --- 7. (Optional) Vizualizarea Rezultatelor ---
plt.figure(figsize=(12, 6))
plt.subplot(1, 2, 1)
plt.plot(range(EPOCHS), history['train_acc'], label='Acuratețe Antrenare')
plt.plot(range(EPOCHS), history['val_acc'], label='Acuratețe Validare')
plt.legend(loc='lower right')
plt.title('Acuratețea Modelului')

plt.subplot(1, 2, 2)
plt.plot(range(EPOCHS), history['train_loss'], label='Pierdere Antrenare')
plt.plot(range(EPOCHS), history['val_loss'], label='Pierdere Validare')
plt.legend(loc='upper right')
plt.title('Pierderea Modelului')
plt.show()