import random
import pandas as pd
from faker import Faker
import datetime
drinkTypes = ["Milk Tea", "Fresh Milk", "Ice Blend", "Fruit Tea", "Mojito", "Creama"]
employees = {"Jason": 0, "Quy": 0, "Quenton": 0, "Jacob":1, "Allie": 0}
subType = {
    "Milk Tea": {
        "Classic Milk Tea": 4.99,
        "Okinawa Milk Tea": 5.49,
        "Hokkaido Milk Tea": 5.49,
        "Coffee Milk Tea": 4.99,
        "Thai Milk Tea": 5.99,
        "Taro Milk Tea": 5.99,
        "Matcha Red Bean Milk Tea": 6.49,
        "Honey Milk Tea": 5.99,
        "Ginger Milk Tea": 5.49,
        "Mango Green Milk Tea": 6.49
    },
    "Fresh Milk": {
        "Fresh Milk Tea": 5.49,
        "Wintermelon with Fresh Milk": 5.99,
        "Cocoa Lover with Fresh Milk": 6.49,
        "Fresh Milk Family": 6.99,
        "Handmade Taro with Fresh Milk": 6.49
    },
    "Ice Blend": {
        "Oreo Ice Blended": 6.99,
        "Milk Tea Ice Blended": 6.49,
        "Taro Ice Blended": 6.99,
        "Thai Tea Ice Blended": 7.49,
        "Matcha Red Bean Ice Blended": 7.49,
        "Coffee Ice Blended": 7.49,
        "Mango Ice Blended": 7.49,
        "Strawberry Ice Blended": 7.49,
        "Peach Tea Ice Blended": 7.49
    },
    "Fruit Tea": {
        "Mango Green Tea": 5.99,
        "Wintermelon Lemonade": 4.99,
        "Strawberry Tea": 4.99,
        "Peach Tea": 4.99,
        "Honey Lemonade": 4.99,
        "Peach Kiwi Tea": 5.49,
        "Kiwi Fruit Tea": 5.49,
        "Mango Passion Fruit Tea": 6.49,
        "Tropical Fruit Tea": 5.99,
        "Hawaii Fruit Tea": 6.49,
        "Passion Fruit, Orange, and Grapefruit Tea": 6.49
    },
    "Mojito": {
        "Lime Mojito": 4.99,
        "Mango Mojito": 4.99,
        "Peach Mojito": 4.99,
        "Strawberry Mojito": 4.99
    },
    "Creama": {
        "Creama Tea": 5.49,
        "Wintermelon Creama": 5.99,
        "Coffee Creama": 5.49,
        "Cocoa Creama": 5.99,
        "Mango Creama": 6.49,
        "Matcha Creama": 6.49
    }
}
def type_weight(drinkType):
    weights = []
    if drinkType == "Milk Tea":
        weights = [10, 3, 1, 1, 3, 5, 3, 2 , 1, 3]
    elif drinkType == "Fresh Milk":
        weights = [5, 10, 3, 3, 2]
    elif drinkType == "Ice Blend":
        weights = [10, 3, 3, 3, 2, 1, 4, 8, 3]
    elif drinkType == "Fruit Tea":
        weights = [7, 4, 3, 3, 4, 3, 2, 7, 5, 6, 2]
    elif drinkType == "Mojito":
        weights = [2, 7, 5, 7]
    elif drinkType == "Creama":
        weights = [5, 3, 1, 1, 1, 1]

toppings = {"Pearls": 0.75, "Mini Pearls": 0.75, "Ice Cream": 1.00, "Pudding": 0.75, "Aloe Vera": 0.75, "Red Bean": 0.75, "Herb Jelly": 0.75, "Aiyu Jelly": 0.75, "Lychee Jelly": 0.75, "Creama": 1.00}
toppingWeights = {"Milk Tea":[10, 4, 1, 3, 2, 6, 3, 3, 2, 1],
                   "Fresh Milk":[10, 4, 1, 3, 2, 6, 3, 3, 2, 1], 
                   "Ice Blend":[10, 4, 1, 3, 2, 6, 3, 3, 7, 1], 
                   "Fruit Tea": [3, 1, 0, 3, 5, 1, 3, 5, 9, 0], 
                   "Mojito":[10, 4, 1, 3, 2, 6, 3, 3, 7, 1], 
                   "Creama":[10, 4, 1, 3, 2, 6, 3, 3, 7, 1]}

def drink_creator():
    price = 0.0
    drinkToppings= []
    drinkWeight = [10, 1, 3, 5, 1, 1]   
    drinkType = random.choices(drinkTypes, weights = drinkWeight)[0]
    typeWeight = type_weight(drinkType)
    drinkSubType = random.choices(list(subType[drinkType].keys()), weights = typeWeight)[0]
    price = subType[drinkType][drinkSubType]
    numToppings = random.choices([0, 1, 2, 3], weights = [0.20, 0.70, 0.07, 0.03])[0] if drinkType != "Mojito" else 0
    for x in range(numToppings):
        topping = random.choices(list(toppings.keys()), weights=toppingWeights[drinkType])[0]
        price += toppings[topping]
        drinkToppings.append(list(toppings.keys()).index(topping))
    iceLevel = random.choices(["Normal", "Less", "No"], weights = [0.4, 0.4, 0.2])[0] if drinkType != "Ice Blend" else "Normal"
    iceLevel += " Ice"
    sweetnessLevel = random.choices([100, 80, 50, 30, 0], weights=[0.3, 0.3, 0.1, 0.1, 0.2])[0]
    return {"type": drinkType, "name": drinkSubType, "price": price, "toppings": drinkToppings, "ice_level":iceLevel, "sweetness":sweetnessLevel}
    
def toppings_script():
    dfCast = {}
    dfCast["Toppings"] = list(toppings.keys())
    dfCast["Price"] = [toppings[x] for x in toppings.keys()]
    df = pd.DataFrame(dfCast)
    df = df.rename_axis("topping_id")
    df.to_csv("Topping")

def customers_script(customers):
    dfCast = {}
    dfCast["Name"] = list(customers.keys())
    #dfCast["Points"] = [customers[x] for x in customers.keys()]
    df = pd.DataFrame(dfCast)
    df = df.rename_axis("customer_id")
    df.to_csv("Customer.csv")



def orders_script(orders):
    # df = pd.DataFrame(columns = ["Customer", "Employee", "Time", "Drinks", "Price"])
    # for order in orders:
    #     df = pd.concat([df, pd.DataFrame(order).transpose()], ignore_index=True)
    # print(orders)
    df = pd.DataFrame(orders)
    df = df.rename_axis("order_id")
    df.to_csv("orders.csv")
    

def employee_script(a):
    df = pd.DataFrame()
    df["name"] = list(a.keys())
    df["manager"] = [a[x] for x in a]
    df = df.rename_axis("employee_id")
    df.to_csv("Employee.csv")

def drinks_script(drinks):
    df = pd.DataFrame(drinks)
    df = df.rename_axis("drink_id")
    df.to_csv("Drink.csv")

def order_creator(customers, time):
    customer = random.choices(list(customers.keys()))[0]
    employee = random.choices(list(employees.keys()))[0]
    drinks = []
    weights = [0.5**i for i in range(1, 101)]
    numOrder = random.choices(list(range(1, 101)), weights=weights)[0]
    for x in range(numOrder):
        drinks.append(drink_creator())
    price = sum([x["price"] for x in drinks])
    # print({"Customer": int(list(customers.keys()).index(customer)), "Employee": int(list(employees.keys()).index(employee)), "Time": time, "Drinks": drinks, "Price":price})
    return ({"customer_id": int(list(customers.keys()).index(customer)), "employee_id": int(list(employees.keys()).index(employee)), "date": time.date(), "time":time.time(), "total_price":price}, drinks)
    
def drink_topping_script(drink, topping):
    df = pd.DataFrame()
    df["drink_id"] = drink
    df["topping_id"] = topping
    df.to_csv("drink_topping.csv")


if __name__ == "__main__":
    orders = []
    fake = Faker()
    customers = {}
    for x in range(100):
        name = fake.name()
        customers[fake.name()] = 0
    startTime = datetime.datetime.now() - datetime.timedelta(days=365)
    # print(startTime)
    # order_creator(customers, startTime)
    drinks = []
    for day in range(1, 366):
        current_date = startTime + datetime.timedelta(days=day - 1)
        while True:
            holder = 200
            if current_date.month == 8 and current_date.day == 21 or current_date.month == 1 and current_date.day == 16:
                holder = 100
            current_date += datetime.timedelta(seconds=random.randint(1, holder))
            order = order_creator(customers, current_date)
            orders.append(order[0])
            drinks.append(order[1])
            if current_date.hour > 22 or current_date.hour < 9:
                break
    for x in range(len(drinks)):
        for y in range(len(drinks[x])):
            drinks[x][y]["order_id"] = x
    flattenedList = [x for y in drinks for x in y]
    drink_ids = []
    topping_ids = []
    for x in range(len(flattenedList)):
        drinkToppings = flattenedList[x].pop("toppings")
        for y in drinkToppings:
            drink_ids.append(x)
            topping_ids.append(y)

    
    print(flattenedList)

    customers_script(customers)
    drink_topping_script(drink_ids, topping_ids)
    employee_script(employees)
    toppings_script()
    orders_script(orders)
    drinks_script(flattenedList)
