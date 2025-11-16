from ultralytics import YOLO
from PIL import Image

# încarcă modelul YOLO antrenat
model = YOLO("runs/detect/train/weights/best.pt")

# alege imaginea de test
image_path = "test_image6.jpg"

# încarcă imaginea (doar ca verificare)
img = Image.open(image_path)

# rulează YOLO
results = model(image_path)

# extrage numărul de saci detectați
total_bags = len(results[0].boxes)

# afișare în consolă
print("––––––––––––––––––––––")
print(f"Total saci detectați: {total_bags}")
print("––––––––––––––––––––––")
