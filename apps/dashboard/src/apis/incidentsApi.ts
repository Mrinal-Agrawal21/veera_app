import { Incident } from '@/types/incident';

export const fetchIncidents = async (): Promise<Incident[]> => {
  const res = await fetch('http://localhost:8080/api/sos');
  const json = await res.json();

  // 🔥 THIS IS THE IMPORTANT PART
  return json.map((item: any) => ({
    id: item.id,
    uid: item.uid,
    location: item.location,
    latitude: item.latitude,
    longitude: item.longitude,
    riskScore: item.riskScore,
    riskLevel:
      item.riskScore >= 70
        ? 'high'
        : item.riskScore >= 40
        ? 'medium'
        : 'low',
    timestamp: new Date(item.timestamp),
    emergencyContactStatus: item.emergencyContactStatus,
  }));
};