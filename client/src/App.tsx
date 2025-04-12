// import { useState } from "react";
import { BarChart } from "@mui/x-charts/BarChart";
import { PieChart } from "@mui/x-charts/PieChart";
import "./App.css";

function App() {
  // const [count, setCount] = useState(0);

  return (
    <>
      <div>
        <BarChart
          xAxis={[
            {
              id: "barCategories",
              data: ["Credit", "Debit"],
              scaleType: "band",
            },
          ]}
          series={[
            {
              data: [2, 5],
            },
          ]}
          width={500}
          height={300}
        />
      </div>

      <div>
        <PieChart
          series={[
            {
              data: [
                { id: 0, value: 10, label: "series A" },
                { id: 1, value: 15, label: "series B" },
                { id: 2, value: 20, label: "series C" },
              ],
            },
          ]}
          width={400}
          height={200}
        />
      </div>
    </>
  );
}

export default App;
