import { useState, useEffect } from "react";
import { BarChart } from "@mui/x-charts/BarChart";
import { PieChart } from "@mui/x-charts/PieChart";
import "./App.css";

function App() {
  const [barData, setBarData] = useState<any>({});
  const [pieData, setPieData] = useState<{ id: number; value: number; label: string }[]>([]);
  const [loading, setLoading] = useState(true); // Loading state


  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch("http://localhost:8080/finance-data");
        const data = await response.json();

        setBarData(data.barChart);
        setPieData(data.pieChart);
      } catch (error) {
        console.error("Error fetching data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

  if (!barData || !barData.columns || !barData.values) {
    return <p>Error: Invalid data format</p>;
  }
  
  return (
    <>
      <div
        style={{
          display: "flex",
          flexDirection: "row",
          gap: "20px",
          border: "1px solid black",
          padding: "20px",
        }}
      >
        {barData ? (
          <div>
            <BarChart
              xAxis={[
                {
                  id: "barCategories",
                  data: barData.columns,
                  scaleType: "band",
                },
              ]}
              series={[
                {
                  data: barData.values,
                },
              ]}
              width={500}
              height={300}
            />
          </div>
        ) : null}

        <div>
        <PieChart
          series={[
            {
              data: pieData,
            },
          ]}
          width={400}
          height={200}
        />
      </div>
      </div>
    </>
  );
}

export default App;
