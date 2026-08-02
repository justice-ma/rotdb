import { useEffect, useMemo } from "react";
import AbilityCalculation from "./pages/AbilityCalculation";
import Header from "./components/Header";
import { recordHeartbeat } from "./api/api";

const CLIENT_ID_KEY = "rotdb-client-id";
const SESSION_ID_KEY = "rotdb-session-id";
const HEARTBEAT_INTERVAL_MS = 30_000;

function getOrCreateStorageId(storage, key) {
  let id = storage.getItem(key);

  if (!id) {
    id = crypto.randomUUID();
    storage.setItem(key, id);
  }

  return id;
}

function App() {
  const clientId = useMemo(
    () => getOrCreateStorageId(localStorage, CLIENT_ID_KEY),
    [],
  );

  const sessionId = useMemo(
    () => getOrCreateStorageId(sessionStorage, SESSION_ID_KEY),
    [],
  );

  useEffect(() => {
    function sendHeartbeat() {
      recordHeartbeat(clientId, sessionId).catch(() => {});
    }

    sendHeartbeat();

    const intervalId = window.setInterval(sendHeartbeat, HEARTBEAT_INTERVAL_MS);

    return () => {
      window.clearInterval(intervalId);
    };
  }, []);

  return (
    <>
      <Header />
      <AbilityCalculation clientId={clientId} sessionId={sessionId} />
    </>
  );
}

export default App;
