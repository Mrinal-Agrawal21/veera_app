export interface User {
  _id: string;
  latitude: number;
  longitude: number;
  address?: string;
  riskScore?: number;
  isInDanger?: boolean;
}

const BASE_URL = "https://veera-core.onrender.com/api/user/user_mrinal_mkc920ad";

/**
 * Fetch all users (for dashboard overview)
 */
export const fetchAllUsers = async (): Promise<User[]> => {
  const res = await fetch(BASE_URL);

  if (!res.ok) {
    throw new Error("Failed to fetch users");
  }

  return res.json();
};

/**
 * Fetch single user by ID (dynamic)
 */
export const fetchUserById = async (userId: string): Promise<User> => {
  const res = await fetch(`${BASE_URL}/${userId}`);

  if (!res.ok) {
    throw new Error("Failed to fetch user by ID");
  }

  return res.json();
};
