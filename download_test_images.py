import os
from pathlib import Path
from ddgs import DDGS     # ✅ correct import for the new package
import requests
from PIL import Image
from io import BytesIO

BASE_DIR = Path(r"C:\Users\Ples Vasile\Desktop\trashbag-not-empty-ai\data\test")
FOLDER_EMPTY = BASE_DIR / "empty"
FOLDER_NON_EMPTY = BASE_DIR / "non_empty"

CLASSES = {
    "empty": [
        "empty trash bag",
        "empty garbage bag",
        "empty black plastic bag",
        "empty trash bag on grass",
        "empty trash bag in bin",
    ],
    "non_empty": [
        "full trash bag",
        "garbage bag full of trash",
        "overflowing trash bags",
        "trash bags full outdoor",
        "stuffed garbage bag",
    ],
}

IMAGES_PER_CLASS = 150

FOLDER_EMPTY.mkdir(parents=True, exist_ok=True)
FOLDER_NON_EMPTY.mkdir(parents=True, exist_ok=True)

def download_images(queries, target_folder, max_count):
    print(f"\n🔍 Searching images for {target_folder} ...")
    downloaded = 0
    seen_urls = set()

    with DDGS() as ddgs:
        for query in queries:
            print(f"→ Query: {query}")
            for result in ddgs.images(
                query=query,                 # ✅ correct parameter name
                max_results=max_count * 3,
                safesearch="moderate",
            ):
                url = result.get("image")
                if not url or url in seen_urls:
                    continue
                seen_urls.add(url)
                try:
                    response = requests.get(url, timeout=10)
                    img = Image.open(BytesIO(response.content)).convert("RGB")
                    save_path = Path(target_folder) / f"{downloaded:03d}.jpg"
                    img.save(save_path, "JPEG", quality=90)
                    downloaded += 1
                    if downloaded >= max_count:
                        print(f"✅ Collected {downloaded} images for {target_folder}\n")
                        return
                except Exception:
                    continue
    print(f"⚠️ Only got {downloaded} images for {target_folder}")

def main():
    download_images(CLASSES["empty"], FOLDER_EMPTY, IMAGES_PER_CLASS)
    download_images(CLASSES["non_empty"], FOLDER_NON_EMPTY, IMAGES_PER_CLASS)
    print("\n🎉 Done! Your test dataset is ready.")

if __name__ == "__main__":
    main()
