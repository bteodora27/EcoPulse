import torch
import torch.nn as nn
from torchvision import models, transforms
from PIL import Image
import os

# --- CONFIGURAREA LOGICII ---
CLASS_LABELS = {0: 'clean', 1: 'dirty'}  # Presupunând 'clean'=0, 'dirty'=1 (alfabetic)
MODEL_PATH = 'clean_dirty_classifier.pth'

device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")

# --- MANAGEMENTUL MODELELOR ---
classifier_model = None

# Transformare standard pentru imagini (fără augmentare)
validation_transform = transforms.Compose([
    transforms.Resize(256),
    transforms.CenterCrop(224),
    transforms.ToTensor(),
    transforms.Normalize([0.485, 0.456, 0.406], [0.229, 0.224, 0.225])
])

def load_classification_model():
    """
    Încarcă DOAR modelul de clasificare în memorie.
    """
    global classifier_model

    print(f"Se încarcă modelul de clasificare din {MODEL_PATH}...")
    try:
        # --- Modelul 1: Pentru Clasificare ---
        classifier_model = models.mobilenet_v2(weights=None)
        in_features = classifier_model.classifier[1].in_features
        classifier_model.classifier = nn.Sequential(
            nn.Dropout(p=0.2),
            nn.Linear(in_features, 1)
        )
        classifier_model.load_state_dict(torch.load(MODEL_PATH, map_location=device))
        classifier_model.to(device)
        classifier_model.eval() # Foarte important: setează modelul în modul de evaluare

        print("Modelul de clasificare PyTorch a fost încărcat cu succes.")

    except Exception as e:
        print(f"Eroare FATALĂ la încărcarea modelului PyTorch: {e}")
        raise e

# --- FUNCȚIILE AJUTĂTOARE (LOGICĂ PURĂ) ---

def preprocess_image(img_path):
    """Încarcă o imagine și o pre-procesează"""
    image = Image.open(img_path).convert('RGB')
    return validation_transform(image).unsqueeze(0).to(device)


def get_image_label(img_path):
    """
    Rulează clasificarea Clean/Dirty pe o imagine.
    Aceasta este acum funcția principală a logicii tale.
    """
    if classifier_model is None:
        raise Exception("Modelul de clasificare nu este încărcat. Rulează load_classification_model().")

    image_tensor = preprocess_image(img_path)

    with torch.no_grad():
        output = classifier_model(image_tensor)
        prob = torch.sigmoid(output)
        prediction = prob > 0.5

    # Returnează eticheta text (ex: "dirty")
    return CLASS_LABELS[1] if prediction.item() else CLASS_LABELS[0]