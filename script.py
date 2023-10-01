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
        drinkToppings.append(topping)
    iceLevel = random.choices(["Normal", "Less", "No"], weights = [0.4, 0.4, 0.2])[0] if drinkType != "Ice Blend" else "Normal"
    iceLevel += " Ice"
    sweetnessLevel = random.choices([100, 80, 50, 30, 0], weights=[0.3, 0.3, 0.1, 0.1, 0.2])[0]
    return {"Drink Type": drinkType, "Drink Name": drinkSubType, "Price": price, "Toppings": drinkToppings, "Ice Level":iceLevel, "Sweetness Level":sweetnessLevel}
    
def toppings_script():
    dfCast = {}
    dfCast["Toppings"] = list(toppings.keys())
    dfCast["Price"] = [toppings[x] for x in toppings.keys()]
    df = pd.DataFrame(dfCast)
    print(df)

def customers_script(customers):
    dfCast = {}
    dfCast["Name"] = list(customers.keys())
    dfCast["Points"] = [customers[x] for x in customers.keys()]
    df = pd.DataFrame(dfCast)

def orders_script(orders):
    df = pd.DataFrame(columns = ["Customer", "Employee", "Time", "Drinks", "Price"])
    for order in orders:
        df = pd.concat([df, pd.DataFrame(order).transpose()], ignore_index=True)
    print(df)
    df.to_csv("test")
    

def order_creator(customers, time):
    customer = random.choices(list(customers.keys()))[0]
    employee = random.choices(list(employees.keys()))[0]
    drinks = []
    weights = [0.5**i for i in range(1, 101)]
    numOrder = random.choices(list(range(1, 101)), weights=weights)[0]
    for x in range(numOrder):
        drinks.append(drink_creator())
    price = sum([x["Price"] for x in drinks])
    print({"Customer": int(list(customers.keys()).index(customer)), "Employee": int(list(employees.keys()).index(employee)), "Time": time, "Drinks": drinks, "Price":price})
    return {"Customer": int(list(customers.keys()).index(customer)), "Employee": int(list(employees.keys()).index(employee)), "Time": time, "Drinks": drinks, "Price":price}
    



if __name__ == "__main__":
    orders = []
    fake = Faker()
    customers = {}
    for x in range(100):
        name = fake.name()
        customers[fake.name()] = 0
    startTime = datetime.datetime.now() - datetime.timedelta(days=365)
    print(startTime)
    order_creator(customers, startTime)
    # for day in range(1, 366):
    #     current_date = startTime + datetime.timedelta(days=day - 1)
    #     while True:
    #         current_date += datetime.timedelta(seconds=random.randint(1, 10000))
    #         orders.append(order_creator(customers, current_date))
    #         if current_date.hour < 22 and current_date.hour > 9:
    #             break
    