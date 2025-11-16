from pathlib import Path
import shutil, random

base_train = Path("data/train")
base_val = Path("data/val")

for cls in ["empty", "non_empty"]:
    src = base_train / cls
    dst = base_val / cls
    dst.mkdir(parents=True, exist_ok=True)

    images = list(src.glob("*.jpg")) + list(src.glob("*.jpeg")) + list(src.glob("*.png"))
    random.shuffle(images)
    val_count = max(1, len(images) // 5)  # take 20%

    for img in images[:val_count]:
        shutil.copy(img, dst / img.name)

print("✅ Validation dataset created under data/val/")
