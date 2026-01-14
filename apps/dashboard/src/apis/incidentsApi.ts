import { Incident } from '@/types/incident';

const CORS_PROXY = 'https://api.allorigins.win/raw?url=';
// Use the user-specific incidents endpoint that exists on the old backend
const API_URL = 'https://veera-core.onrender.com/api/user/user_mrinal_mkc920ad';

export const fetchIncidents = async (): Promise<Incident[]> => {
  try {
    const res = await fetch(CORS_PROXY + encodeURIComponent(API_URL));
    
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