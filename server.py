import uvicorn
from fastapi import FastAPI, File, UploadFile, HTTPException
import os
from contextlib import asynccontextmanager

# --- 1. IMPORTĂ LOGICA (funcțiile simplificate) ---
from ai_logic import load_classification_model, get_image_label


# --- 2. DEFINEȘTE FUNCȚIA LIFESPAN (Modern) ---
@asynccontextmanager
async def lifespan(app: FastAPI):
    # Codul care rulează o singură dată, la pornire
    print("Se execută evenimentul de pornire (lifespan)...")
    load_classification_model()  # Apelăm noua funcție de încărcare
    yield
    # Codul care rulează la oprire (dacă e nevoie)
    print("Se execută evenimentul de oprire...")


# --- 3. INIȚIAZĂ APLICAȚIA (cu lifespan) ---
app = FastAPI(
    title="Ecopulse AI Classification Service (Simplificat)",
    lifespan=lifespan
)


# --- 4. NOUL ENDPOINT (Simplificat) ---
@app.post("/classify_image")
async def handle_classification_request(
        image: UploadFile = File(...)  # Așteaptă o singură imagine
):
    print("\n[INFO] Am primit o nouă cerere de clasificare...")

    # Folosim un nume de fișier temporar
    temp_path = "temp_image_to_classify"

    try:
        # --- A. Salvează fișierul temporar ---
        ext = os.path.splitext(image.filename)[1]
        temp_path += ext

        with open(temp_path, "wb") as f:
            f.write(await image.read())

        # --- B. Apelează logica AI ---
        # Apelează funcția simplă de clasificare
        classification_label = get_image_label(temp_path)

        # --- C. Returnează rezultatul ---
        print(f"[INFO] REZULTAT FINAL: {classification_label}")
        return {
            "status": "success",
            "classification": classification_label
        }

    except Exception as e:
        print(f"[EROARE] Eroare server 500: {e}")
        raise HTTPException(status_code=500, detail={"status": "error", "reason": str(e)})

    finally:
        # --- D. Șterge fișierul temporar ---
        if os.path.exists(temp_path):
            os.remove(temp_path)


# --- 5. COMANDA DE RULARE A SERVERULUI ---
if __name__ == "__main__":
    # Folosim "127.0.0.1" (localhost) pentru testare locală
    # Schimbă la "0.0.0.0" pentru a-l face accesibil în rețea
    uvicorn.run(app, host="0.0.0.0", port=5000)