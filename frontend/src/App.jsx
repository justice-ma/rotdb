import { useState } from "react";
import AbilityCalculation from "./pages/AbilityCalculation";
import Header from "./components/Header";

function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      <Header />
      <AbilityCalculation />
    </>
  );
}

export default App;
