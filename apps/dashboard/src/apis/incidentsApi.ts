import { Incident } from '@/types/incident';

export const fetchIncidents = async (): Promise<Incident[]> => {
  try {
    const res = await fetch('http://localhost:8080/api/sos');
    
    if (!res.ok) {
      console.error(`Failed to fetch incidents: ${res.status} ${res.statusText}`);
      return [];
    }

    const json = await res.json();

    // Handle both array and object responses
    const data = Array.isArray(json) ? json : json.data || json.incidents || [];

    if (!Array.isArray(data)) {
      console.error('Invalid response format:', json);
      return [];
    }

    return data.map((item: any) => ({
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
  } catch (error) {
    console.error('Error fetching incidents:', error);
    return [];
  }
};