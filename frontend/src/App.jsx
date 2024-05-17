import { useEffect, useState } from "react";
import { getItems } from "./item_service";

const App = () => {
  const [items, setItems] = useState([]);

  useEffect(() => {
    getItems().then((result) => setItems(result.data));
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
