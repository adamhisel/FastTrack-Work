export function calculateTotal (items, tax) {
  let total = 0.0;
  let absTax = Math.abs(tax);
  for(let item of items){
    let value = item.price;
    if(item.taxable){
      value += value*absTax;
    }
    total += value;
  }
  return total;
}