from fastapi import FastAPI, HTTPException
import os
import joblib
import pandas as pd
import sys

# Add parent directory to path for imports
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from api.schemas import RiskRequest
from utils.risk_utils import calculate_risk

app = FastAPI()

# -----------------------------
# Load model once
# -----------------------------
BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
MODEL_PATH = os.path.join(BASE_DIR, "model", "women_safety_xgb_model.pkl")

model = joblib.load(MODEL_PATH)
print("MODEL CLASSES:", model.classes_)  # [0 1 2]


@app.post("/predict")
def predict_risk(data: RiskRequest):
    try:
        df = pd.DataFrame([{
            "latitude": float(data.latitude),
            "longitude": float(data.longitude),
            "hour": int(data.hour),
            "crime_density": float(data.crime_density),
            "poi_count": int(data.poi_count),

            # ✅ CORRECT: camelCase → snake_case conversion
            "is_night": int(data.isNight),
            "is_isolated": int(data.isIsolated)
        }])

        row = df.iloc[0].to_dict()
        print("RULE INPUT:", row)

        probs = model.predict_proba(df)[0]

        class_index = list(model.classes_)
        class_prob_map = {
            "LOW": float(probs[class_index.index(0)]),
            "MEDIUM": float(probs[class_index.index(1)]),
            "HIGH": float(probs[class_index.index(2)])
        }

        high_prob = class_prob_map["HIGH"]

        score, level = calculate_risk(row, high_prob)

        return {
            "risk_score": int(score),
            "risk_level": level
        }

    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))
