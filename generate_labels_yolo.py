from ultralytics import YOLO
from pathlib import Path
from PIL import Image
import shutil
import os

# === SETĂRI ===
MODEL_PATH = "yolo11x.pt"   # model YOLO pre-antrenat (cel mai bun)
CONF = 0.25                  # threshold detecție
SOURCE_DIR = Path("yolo_dataset/train/images")
LABEL_DIR = Path("yolo_dataset/train/labels")

LABEL_DIR.mkdir(parents=True, exist_ok=True)

# YOLO class id (noi avem 1 singură clasă: "bag")
CLASS_ID = 0

# === ÎNCARCĂ MODELUL ===
model = YOLO(MODEL_PATH)

def convert_to_yolo(x1, y1, x2, y2, W, H):
    xc = (x1 + x2) / 2 / W
    yc = (y1 + y2) / 2 / H
    w = (x2 - x1) / W
    h = (y2 - y1) / H
    return xc, yc, w, h


# === PROCESARE IMAGINI ===
count = 0
for img_path in SOURCE_DIR.glob("*.jpg"):

    img = Image.open(img_path)
    W, H = img.size

    # YOLO detect
    results = model(img_path, conf=CONF)[0]

    label_path = LABEL_DIR / f"{img_path.stem}.txt"
    f = open(label_path, "w")

    detections = 0

    for box in results.boxes:
        cls_name = model.names[int(box.cls[0])].lower()

        # aici acceptăm TOT ce YOLO consideră "bag", "handbag", "plastic bag", etc.
        if "bag" not in cls_name:
            continue

        x1, y1, x2, y2 = box.xyxy[0].tolist()
        xc, yc, w, h = convert_to_yolo(x1, y1, x2, y2, W, H)

        f.write(f"{CLASS_ID} {xc} {yc} {w} {h}\n")
        detections += 1

    f.close()

    print(f"[OK] {img_path.name} → {detections} bags detected.")
    count += 1

print(f"\nDONE! Generated labels for {count} images!")
