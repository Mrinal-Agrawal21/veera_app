import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import DashboardHeader from "@/components/veera/DashboardHeader";
import IncidentList from "@/components/veera/IncidentList";
import IncidentMap from "@/components/veera/IncidentMap";
import IncidentDetails from "@/components/veera/IncidentDetails";

import { Sheet, SheetContent, SheetHeader, SheetTitle } from "@/components/ui/sheet";

import { fetchIncidents } from "@/apis/incidentsApi";
import { fetchAllUsers } from "@/apis/userApi";

import type { Incident } from "@/types/incident";
import type { User } from "@/apis/userApi";

const Dashboard = () => {
  const navigate = useNavigate();

  const [incidents, setIncidents] = useState<Incident[]>([]);
  const [users, setUsers] = useState<User[]>([]);
  const [selectedIncident, setSelectedIncident] = useState<Incident | null>(null);

  const [loading, setLoading] = useState(true);
  const [isDetailsOpen, setIsDetailsOpen] = useState(false);

  // 🔐 Auth check
  useEffect(() => {
    const session = localStorage.getItem("veera_session");
    if (!session) {
      navigate("/");
    }
  }, [navigate]);

  // 🚨 Fetch incidents
  useEffect(() => {
    const loadIncidents = async () => {
      try {
        const data = await fetchIncidents();
        setIncidents(data);
      } catch (err) {
        console.error("Failed to fetch incidents", err);
      }
    };

    loadIncidents();
  }, []);

  // 👤 Fetch live users
  useEffect(() => {
    const loadUsers = async () => {
      try {
        const data = await fetchAllUsers();
        console.log("LIVE USERS FROM BACKEND:", data); // 👈 verify here
        setUsers(data);
      } catch (err) {
        console.error("Failed to fetch users", err);
      }
    };

    loadUsers();
  }, []);

  // ⏳ Stop loader only when both are ready
  useEffect(() => {
    if (incidents.length >= 0 && users.length >= 0) {
      setLoading(false);
    }
  }, [incidents, users]);

  const handleSelectIncident = (incident: Incident) => {
    setSelectedIncident(incident);
    if (window.innerWidth < 1024) {
      setIsDetailsOpen(true);
    }
  };

  const handleCloseDetails = () => {
    setSelectedIncident(null);
    setIsDetailsOpen(false);
  };

  if (loading) {
    return (
      <div className="h-screen flex items-center justify-center">
        <p className="text-muted-foreground text-sm">Loading dashboard…</p>
      </div>
    );
  }

  return (
    <div className="h-screen flex flex-col bg-background overflow-hidden">
      {/* Header */}
      <DashboardHeader />

      {/* Main Layout */}
      <div className="flex-1 flex overflow-hidden">
        {/* Left Panel */}
        <div className="w-[340px] shrink-0 hidden md:block">
          <IncidentList
            incidents={incidents}
            selectedId={selectedIncident?.id ?? null}
            onSelect={handleSelectIncident}
          />
        </div>

        {/* Center Panel – Map */}
        <div className="flex-1 min-w-0">
          <IncidentMap
            incidents={incidents}
            users={users}
            selectedIncident={selectedIncident}
            onSelectIncident={handleSelectIncident}
          />
        </div>

        {/* Right Panel – Details */}
        <div className="w-[340px] shrink-0 hidden lg:block">
          <IncidentDetails
            incident={selectedIncident}
            onClose={handleCloseDetails}
          />
        </div>
      </div>

      {/* Mobile Bottom Sheet – Incident List */}
      <div className="md:hidden fixed bottom-0 left-0 right-0 bg-card border-t border-border max-h-[45vh] overflow-auto rounded-t-3xl shadow-2xl">
        <div className="sticky top-0 bg-card pt-3 pb-2 px-4 border-b border-border/50">
          <div className="w-10 h-1 bg-muted rounded-full mx-auto mb-2" />
        </div>
        <IncidentList
          incidents={incidents}
          selectedId={selectedIncident?.id ?? null}
          onSelect={handleSelectIncident}
        />
      </div>

      {/* Tablet Details Sheet */}
      <Sheet
        open={isDetailsOpen && window.innerWidth < 1024}
        onOpenChange={setIsDetailsOpen}
      >
        <SheetContent className="w-full sm:max-w-md p-0">
          <SheetHeader className="sr-only">
            <SheetTitle>Incident Details</SheetTitle>
          </SheetHeader>
          <IncidentDetails
            incident={selectedIncident}
            onClose={handleCloseDetails}
          />
        </SheetContent>
      </Sheet>
    </div>
  );
};

export default Dashboard;
