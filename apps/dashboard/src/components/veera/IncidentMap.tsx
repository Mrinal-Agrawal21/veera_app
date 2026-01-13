import { useEffect, useRef } from "react";
import { Incident } from "@/types/incident";
import { User } from "@/apis/userApi";

interface IncidentMapProps {
  incidents: Incident[];
  users: User[];
  selectedIncident: Incident | null;
  onSelectIncident: (incident: Incident) => void;
}

const GOOGLE_MAPS_API_KEY = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;

const IncidentMap = ({
  incidents,
  users,
  selectedIncident,
  onSelectIncident,
}: IncidentMapProps) => {
  const mapRef = useRef<HTMLDivElement | null>(null);
  const mapInstance = useRef<google.maps.Map | null>(null);
  const markersRef = useRef<google.maps.Marker[]>([]);

  // 1️⃣ Load Google Maps script
  useEffect(() => {
    if (!GOOGLE_MAPS_API_KEY) {
      console.error("❌ Google Maps API key missing");
      return;
    }

    if (window.google?.maps) {
      initMap();
      return;
    }

    const script = document.createElement("script");
    script.src = `https://maps.googleapis.com/maps/api/js?key=${GOOGLE_MAPS_API_KEY}`;
    script.async = true;
    script.defer = true;
    script.onload = initMap;

    document.body.appendChild(script);
  }, []);

  // 2️⃣ Initialize map
  const initMap = () => {
    if (!mapRef.current || mapInstance.current) return;

    mapInstance.current = new google.maps.Map(mapRef.current, {
      center: { lat: 22.9734, lng: 78.6569 }, // India center
      zoom: 5,
      disableDefaultUI: true,
      zoomControl: true,
    });
  };

  // 3️⃣ Render markers whenever data changes
  useEffect(() => {
    if (!mapInstance.current) return;

    // Clear old markers
    markersRef.current.forEach((m) => m.setMap(null));
    markersRef.current = [];

    // INCIDENT MARKERS (RED / ORANGE / GREEN)
    incidents.forEach((incident) => {
      const color =
        incident.riskLevel === "high"
          ? "red"
          : incident.riskLevel === "medium"
          ? "orange"
          : "green";

      const marker = new google.maps.Marker({
        position: {
          lat: incident.latitude,
          lng: incident.longitude,
        },
        map: mapInstance.current!,
        title: incident.uid,
        icon: {
          path: google.maps.SymbolPath.CIRCLE,
          scale: 8,
          fillColor: color,
          fillOpacity: 1,
          strokeWeight: 2,
          strokeColor: "#fff",
        },
      });

      marker.addListener("click", () => onSelectIncident(incident));
      markersRef.current.push(marker);
    });

    // USER MARKERS (BLUE)
    users.forEach((user) => {
      if (!user.latitude || !user.longitude) return;

      const marker = new google.maps.Marker({
        position: {
          lat: user.latitude,
          lng: user.longitude,
        },
        map: mapInstance.current!,
        title: user.address || "User location",
        icon: {
          path: google.maps.SymbolPath.BACKWARD_CLOSED_ARROW,
          scale: 6,
          fillColor: "#2563eb",
          fillOpacity: 1,
          strokeWeight: 1,
          strokeColor: "#fff",
        },
      });

      markersRef.current.push(marker);
    });
  }, [incidents, users]);

  // 4️⃣ Center map on selected incident
  useEffect(() => {
    if (!selectedIncident || !mapInstance.current) return;

    mapInstance.current.panTo({
      lat: selectedIncident.latitude,
      lng: selectedIncident.longitude,
    });
    mapInstance.current.setZoom(12);
  }, [selectedIncident]);

  return (
    <div className="h-full flex flex-col bg-muted/20">
      <div className="flex-1 m-4 rounded-2xl overflow-hidden border border-border/50 shadow-inner">
        <div ref={mapRef} className="w-full h-full" />
      </div>
    </div>
  );
};

export default IncidentMap;
