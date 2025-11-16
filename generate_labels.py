from ultralytics import YOLO
from pathlib import Path
import shutil

# === CONFIG ===
IMAGE_DIR = Path("yolo_dataset/train/images")
LABEL_DIR = Path("yolo_dataset/train/labels")
LABEL_DIR.mkdir(parents=True, exist_ok=True)

# Încarcă YOLO pre-antrenat (poți folosi yolo11s.pt sau yolo11x.pt)
model = YOLO("yolo11x.pt")

# Clase relevante (YOLO11 detectează "plastic bag", "garbage bag", "bin", etc.)
TARGET_CLASSES = ["plastic bag", "trash bag", "garbage bag", "bin"]

print("\n🔍 Generating YOLO bounding boxes...")

for img_path in IMAGE_DIR.glob("*.*"):

    results = model(img_path)
    r = results[0]

    out_txt = LABEL_DIR / f"{img_path.stem}.txt"

    with open(out_txt, "w") as f:
        for box in r.boxes:
            cls_id = int(box.cls[0])
            cls_name = model.names[cls_id]

            # Ne interesează DOAR sacii
            if cls_name not in TARGET_CLASSES:
                continue

            x_center, y_center, w, h = box.xywhn[0]

            # cls=0 pentru "trashbag"
            f.write(f"0 {x_center:.6f} {y_center:.6f} {w:.6f} {h:.6f}\n")

    print(f"✔ Labels created: {out_txt.name}")

print("\n🎉 GATA! YOLO label files generated în:")
print("   yolo_dataset/train/labels")
