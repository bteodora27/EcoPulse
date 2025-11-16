from PIL import Image, ImageDraw
from pathlib import Path
import random

# Define folder structure
root = Path("data")
splits = ["train", "val"]
classes = ["empty", "non_empty"]

# Create folders if missing
for split in splits:
    for cls in classes:
        (root / split / cls).mkdir(parents=True, exist_ok=True)

def make_trashbag_image(save_path, fill_level="empty"):
    """
    Generates a simple dummy image representing a trash bag.
    It's just a colored rectangle with 'EMPTY' or 'FILLED' text.
    """
    w, h = 256, 256
    color_bg = (230, 230, 230)
    color_bag = (40, 40, 40) if fill_level == "non_empty" else (100, 100, 100)
    img = Image.new("RGB", (w, h), color_bg)
    draw = ImageDraw.Draw(img)

    # Draw a simple "trash bag" rectangle
    bag_top = random.randint(80, 100)
    bag_bottom = random.randint(170, 200)
    draw.ellipse((80, bag_top, 180, bag_bottom), fill=color_bag)

    # Label it
    text = "NON_EMPTY" if fill_level == "non_empty" else "EMPTY"
    draw.text((90, 220), text, fill=(0, 0, 0))

    img.save(save_path, "JPEG")
    print("✅ Created:", save_path)

# Generate 2 images for each class per split
for split in splits:
    for cls in classes:
        for i in range(1, 3):
            path = root / split / cls / f"{cls}_{i}.jpg"
            make_trashbag_image(path, fill_level=cls)

print("\n🎉 Dummy dataset created successfully!")
