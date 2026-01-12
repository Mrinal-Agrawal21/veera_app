from pydantic import BaseModel, Field

class RiskRequest(BaseModel):
    latitude: float
    longitude: float
    hour: int

    crime_density: float = Field(..., alias="crime_density")
    poi_count: int = Field(..., alias="poi_count")

    isNight: bool
    isIsolated: bool

    class Config:
        populate_by_name = True
