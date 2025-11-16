"""
Safe image downloader for your trash-bag dataset.
Uses the updated ddgs library with backoff to avoid rate limits.
"""

from ddgs import DDGS
from pathlib import Path
import requests, time, random, shutil
from PIL import Image
from io import BytesIO

queries = {
    "empty": ["empty trash bag", "empty garbage bag", "unused trash bag"],
    "non_empty": ["full trash bag", "garbage bag full of trash", "overflowing trash bag"]
}

root = Path("data")
splits = ["train", "val", "test"]
for split in splits:
    for cls in queries:
        (root / split / cls).mkdir(parents=True, exist_ok=True)

def download_images(term, dest, max_count=10):
    """Download images with retries and delay to avoid rate-limits"""
    print(f"🔍 Searching: {term}")
    count = 0
    for attempt in range(3):  # retry a few times if rate-limited
        try:
            with DDGS() as ddgs:
                for r in ddgs.images(keywords=term, max_results=max_count):
                    url = r.get("image")
                    if not url:
                        continue
                    try:
                        img = Image.open(BytesIO(requests.get(url, timeout=10).content)).convert("RGB")
                        path = dest / f"{term.replace(' ', '_')}_{count}.jpg"
                        img.save(path, "JPEG")
                        print(f"✅ Saved {path}")
                        count += 1
                        time.sleep(random.uniform(0.5, 1.5))
                    except Exception as e:
                        print(f"⚠️ Skipping one image: {e}")
            break
        except Exception as e:
            print(f"Rate limit or network error: {e}. Retrying in 10s...")
            time.sleep(10)
