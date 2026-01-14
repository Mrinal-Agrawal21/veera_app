export interface User {
  _id: string;
  latitude: number;
  longitude: number;
  address?: string;
  riskScore?: number;
  isInDanger?: boolean;
}

const CORS_PROXY = 'https://api.allorigins.win/raw?url=';
const BASE_URL = CORS_PROXY + encodeURIComponent('https://veera-core.onrender.com/api/user');


/**
 * Fetch all users (for dashboard overview)
 */
export const fetchAllUsers = async (): Promise<User[]> => {
  try {
    const res = await fetch(BASE_URL);

    if (!res.ok) {
      console.error(`Failed to fetch users: ${res.status} ${res.statusText}`);
      return [];
    }

    const data = await res.json();
    return Array.isArray(data) ? data : [];
  } catch (error) {
    console.error('Error fetching users:', error);
    return [];
  }
};

/**
 * Fetch single user by ID (dynamic)
 */
export const fetchUserById = async (userId: string): Promise<User> => {
  const res = await fetch(`${BASE_URL}/${userId}`)
;

  if (!res.ok) {
    throw new Error("Failed to fetch user by ID");
  }

  return res.json();
};
