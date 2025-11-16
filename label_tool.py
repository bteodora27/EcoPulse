import cv2
import os
from pathlib import Path

IMAGE_DIR = Path("yolo_dataset/train/images")
LABEL_DIR = Path("yolo_dataset/train/labels")

LABEL_DIR.mkdir(parents=True, exist_ok=True)

drawing = False
x1, y1, x2, y2 = -1, -1, -1, -1

def normalize_bbox(x1, y1, x2, y2, img_w, img_h):
    xc = (x1 + x2) / 2 / img_w
    yc = (y1 + y2) / 2 / img_h
    w = abs(x2 - x1) / img_w
    h = abs(y2 - y1) / img_h
    return xc, yc, w, h

def mouse_event(event, x, y, flags, param):
    global drawing, x1, y1, x2, y2

    if event == cv2.EVENT_LBUTTONDOWN:
        drawing = True
        x1, y1 = x, y

    elif event == cv2.EVENT_MOUSEMOVE and drawing:
        x2, y2 = x, y

    elif event == cv2.EVENT_LBUTTONUP:
        drawing = False
        x2, y2 = x, y


def main():
    images = sorted(list(IMAGE_DIR.glob("*.jpg")))
    if not images:
        print("⚠️ No images found!")
        return

    print("🔥 Label Tool Started!")
    print("Controls:")
    print("  - Click & drag = draw box")
    print("  - S = save label")
    print("  - N = next image")
    print("  - Q = quit")

    for img_path in images:
        global x1, y1, x2, y2
        x1 = y1 = x2 = y2 = -1

        img = cv2.imread(str(img_path))
        clone = img.copy()

        cv2.namedWindow("LabelTool")
        cv2.setMouseCallback("LabelTool", mouse_event)

        while True:
            display = clone.copy()

            if x1 != -1 and x2 != -1:
                cv2.rectangle(display, (x1, y1), (x2, y2), (0,255,0), 2)

            cv2.imshow("LabelTool", display)
            key = cv2.waitKey(10)

            if key == ord('s') and x1 != -1 and x2 != -1:
                h, w = img.shape[:2]
                xc, yc, bw, bh = normalize_bbox(x1, y1, x2, y2, w, h)

                label_path = LABEL_DIR / (img_path.stem + ".txt")
                with open(label_path, "w") as f:
                    f.write(f"0 {xc:.6f} {yc:.6f} {bw:.6f} {bh:.6f}")

                print(f"✔ Saved: {label_path.name}")
                break

            elif key == ord('n'):
                print(f"⏭ Skipped {img_path.name}")
                break

            elif key == ord('q'):
                print("👋 Exiting…")
                cv2.destroyAllWindows()
                return

        cv2.destroyWindow("LabelTool")

    print("🎉 DONE!")

if __name__ == "__main__":
    main()
