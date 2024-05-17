import { useEffect, useState } from "react";
import axios from "axios";

const App = () => {
  const [items, setItems] = useState([]);

  useEffect(() => {
    axios("http://localhost:8080").then((result) => setItems(result.data));
  }, []);

  return (
    <>
      <p>Items</p>
      <ol>
        {items.map((item) => (
          <li key={item.id}>
            {item.name} €{item.price}
          </li>
        ))}
      </ol>
    </>
  );
};

export default App;
