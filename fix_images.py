from PIL import Image, UnidentifiedImageError
from pathlib import Path
import shutil

root = Path("data")
valid_exts = [".jpg", ".jpeg", ".png", ".bmp", ".tif", ".tiff", ".webp"]

bad_files = []
for path in root.rglob("*.*"):
    if path.suffix.lower() in valid_exts:
        try:
            # Try to open and re-save to ensure valid format
            img = Image.open(path)
            img.convert("RGB").save(path, format="JPEG")
        except UnidentifiedImageError:
            bad_files.append(path)
        except Exception as e:
            print("⚠️ Unexpected error:", e)

# Remove or move broken ones
if bad_files:
    bad_dir = Path("bad_images")
    bad_dir.mkdir(exist_ok=True)
    for f in bad_files:
        print("🗑️ Moving unreadable file:", f)
        shutil.move(str(f), bad_dir / f.name)
else:
    print("✅ All images are valid and re-saved as proper JPEGs!")
