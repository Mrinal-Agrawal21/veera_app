def calculate_risk(row: dict, high_prob: float):
    """
    Final risk calculation logic.
    SINGLE source of truth.
    crime_density is NORMALIZED (0.0 – 1.0)
    """

    crime = float(row["crime_density"])
    night = bool(row["is_night"])
    isolated = bool(row["is_isolated"])

    # -----------------------------
    # HARD SAFETY OVERRIDES (CRITICAL)
    # -----------------------------
    if crime >= 0.8:
        return 95, "HIGH"

    if crime >= 0.6 and night and isolated:
        return 90, "HIGH"

    if crime >= 0.3:
        return 55, "MEDIUM"

    # -----------------------------
    # ML-DRIVEN SCORE
    # -----------------------------
    score = int(high_prob * 100)

    # -----------------------------
    # SCORE → LEVEL
    # -----------------------------
    if score >= 75:
        return score, "HIGH"
    elif score >= 45:
        return score, "MEDIUM"
    else:
        return score, "LOW"
