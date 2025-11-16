import os
from pathlib import Path

# === CONFIG ===
BASE_DIR = Path(r"C:\Users\Ples Vasile\Desktop\trashbag-not-empty-ai\data")
TARGET_FOLDERS = ["train", "test", "val"]  # choose which ones to clear
IMAGE_EXTENSIONS = {".jpg", ".jpeg", ".png", ".bmp", ".tiff", ".webp"}

def clear_images(base_dir, folders):
    total_deleted = 0
    for folder_name in folders:
        target = base_dir / folder_name
        if not target.exists():
            print(f"⚠️  Folder not found: {target}")
            continue

        print(f"\n🧹 Scanning: {target}")
        for path in target.rglob("*"):
            if path.suffix.lower() in IMAGE_EXTENSIONS:
                try:
                    path.unlink()
                    total_deleted += 1
                except Exception as e:
                    print(f"⚠️ Couldn't delete {path}: {e}")

    print(f"\n✅ Done! Deleted {total_deleted} image files total.")


if __name__ == "__main__":
    clear_images(BASE_DIR, TARGET_FOLDERS)
