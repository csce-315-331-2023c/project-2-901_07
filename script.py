import random
import pandas as pd
from faker import Faker
import datetime
drinkTypes = ["Milk Tea", "Fresh Milk", "Ice Blend", "Fruit Tea", "Mojito", "Creama"]
employees = {"Jason": 0, "Quy": 0, "Quenton": 0, "Jacob":1, "Allie": 0}
subType = {
    "Milk Tea": {
        "Classic Milk Tea": (4.99, ["Creamer", "Black Tea", "Brown Sugar Syrup"]),
        "Okinawa Milk Tea": (5.49, ["Creamer", "Okinawa", "Black Tea", "Syrup"]),
        "Hokkaido Milk Tea": (5.49, ["Creamer", "Hokkaido", "Black Tea", "Syrup"]),
        "Coffee Milk Tea": (4.99, ["Creamer", "Black Tea", "Coffee", "Syrup"]),
        "Thai Milk Tea": (5.99, ["Creamer", "Black Tea", "Thai", "Syrup"]),
        "Taro Milk Tea": (5.99, ["Creamer", "Taro", "Green Tea", "Syrup"]),
        "Matcha Red Bean Milk Tea": (6.49, ["Creamer", "Matcha", "Syrup"]),
        "Honey Milk Tea": (5.99, ["Creamer", "Black Tea", "Honey"]),
        "Ginger Milk Tea": (5.49,["Creamer", "Black Tea", "Ginger", "Syrup"]),
        "Mango Green Milk Tea": (6.49, ["Creamer", "Green Tea", "Mango", "Syrup"])
    },
    "Fresh Milk": {
        "Fresh Milk Tea": (5.49, ["Milk", "Black Tea", "Syrup"]),
        "Wintermelon with Fresh Milk": (5.99, ["Milk", "Wintermelon", "Syrup"]),
        "Cocoa Lover with Fresh Milk": (6.49, ["Milk", "Cocoa", "Syrup"]),
        "Fresh Milk Family": (6.99, ["Milk", "Brown Sugar Syrup"]),
        "Handmade Taro with Fresh Milk": (6.49, ["Milk", "Real Taro", "Syrup"])
    },
    "Ice Blend": {
        "Oreo Ice Blended": (6.99, ["Creamer", "Oreo", "Hokkaido", "Syrup"]),
        "Milk Tea Ice Blended": (6.49, ["Creamer", "Black Tea", "Okinawa"]),
        "Taro Ice Blended": (6.99, ["Creamer", "Taro", "Syrup"]),
        "Thai Tea Ice Blended": (7.49, ["Creamer", "Thai", "Black Tea", "Syrup"]),
        "Matcha Red Bean Ice Blended": (7.49, ["Creamer", "Matcha", "Syrup"]),
        "Coffee Ice Blended": (7.49, ["Creamer", "Black Tea", "Coffee", "Syrup"]),
        "Mango Ice Blended": (7.49,["Mango", "Syrup"]),
        "Strawberry Ice Blended": (7.49, ["Strawberry"]),
        "Peach Tea Ice Blended": (7.49, ["Peach", "Syrup"])
    },
    "Fruit Tea": {
        "Mango Green Tea": (5.99, ["Green Tea", "Mango", "Syrup"]),
        "Wintermelon Lemonade": (4.99, ["Wintermelon", "Lemon Juice", "Syrup"]),
        "Strawberry Tea": (4.99, ["Green Tea", "Strawberry", "Honey"]),
        "Peach Tea": (4.99, ["Green Tea", "Peach", "Syrup"]),
        "Honey Lemonade": (4.99, ["Honey", "Lemon Juice", "Syrup"]),
        "Peach Kiwi Tea": (5.49, ["Green Tea", "Peach", "Kiwi", "Syrup"]),
        "Kiwi Fruit Tea": (5.49, ["Green Tea", "Kiwi", "Syrup"]),
        "Mango Passion Fruit Tea": (6.49, ["Green Tea", "Mango", "Passion Fruit", "Syrup"]),
        "Tropical Fruit Tea": (5.99, ["Green Tea", "Passion Fruit", "Orange", "Syrup"]),
        "Hawaii Fruit Tea": (6.49, ["Green Tea", "Pineapple", "Orange", "Grapefruit", "Syrup"]),
        "Passion Fruit, Orange, and Grapefruit Tea": (6.49, ["Passion Fruit", "Orange", "Grapefruit", "Green Tea", "Syrup"])
    },
    "Mojito": {
        "Lime Mojito": (4.99, ["Lime", "Mint", "Green Tea", "Syrup", "Lime"]),
        "Mango Mojito": (4.99, ["Mango", "Mint", "Green Tea", "Syrup", "Lime"]),
        "Peach Mojito": (4.99, ["Peach", "Mint", "Green Tea", "Syrup", "Lime"]),
        "Strawberry Mojito": (4.99, ["Strawberry", "Mint", "Green Tea", "Syrup", "Lime"])
    },
    "Creama": {
        "Tea": (5.49, ["Black Tea","Syrup"]),
        "Wintermelon Creama": (5.99, ["Wintermelon","Syrup"]),
        "Coffee Creama": (5.49, ["Coffee", "Syrup"]),
        "Cocoa Creama": (5.99, ["Cocoa", "Syrup"]),
        "Mango Creama": (6.49, ["Mango", "Syrup"]),
        "Matcha Creama": (6.49, ["Matcha", "Syrup"])
    }
}

ingredients = {
    "Black Tea": 100,
    "Real Taro": 100,
    "Green Tea": 100,
    "Lemon Juice": 100,
    "Okinawa": 100, 
    "Hokkaido": 100, 
    "Coffee": 100,
    "Thai": 100,
    "Taro": 100,
    "Matcha": 100, 
    "Honey": 100,
    "Ginger": 100, 
    "Mango": 100,
    "Wintermelon":100,
    "Cocoa":100,
    "Oreo":100,
    "Strawberry":100,
    "Peach":100,
    "Kiwi":100,
    "Passion Fruit":100,
    "Pineapple": 100,
    "Orange":100,
    "Grapefruit":100,
    "Lime":100,
    "Brown Sugar Syrup":100,
    "Syrup":100,
    "Milk":100,
    "Mint":100,
    "Creamer":100
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

toppings = {"Pearls": (0.75, ["Tapioca"]), 
            "Mini Pearls": (0.75, ["Mini Tapioca"]), 
            "Ice Cream": (1.00, ["Ice Cream"]), 
            "Pudding": (0.75,["Pudding"]), 
            "Aloe Vera": (0.75, ["Aloe"]), 
            "Red Bean": (0.75, ["Red Bean"]), 
            "Herb Jelly": (0.75, ["Herb Jelly"]), 
            "Aiyu Jelly": (0.75, ["Aiyu Jelly"]), 
            "Lychee Jelly": (0.75, ["Lychee Jelly"]),
            "Creama": (1.00,["Creama"])}

toppingWeights = {"Milk Tea":[10, 4, 1, 3, 2, 6, 3, 3, 2, 1],
                   "Fresh Milk":[10, 4, 1, 3, 2, 6, 3, 3, 2, 1], 
                   "Ice Blend":[10, 4, 1, 3, 2, 6, 3, 3, 7, 1], 
                   "Fruit Tea": [3, 1, 0, 3, 5, 1, 3, 5, 9, 0], 
                   "Mojito":[10, 4, 1, 3, 2, 6, 3, 3, 7, 1], 
                   "Creama":[10, 4, 1, 3, 2, 6, 3, 3, 7, 1]}


def drink_creator(menu_item_df):
    
    price = 0.0
    drinkToppings= []
    drinkWeight = [10, 1, 3, 5, 1, 1]   
    drinkType = random.choices(drinkTypes, weights = drinkWeight)[0]
    typeWeight = type_weight(drinkType)
    drinkSubType = random.choices(list(subType[drinkType].keys()), weights = typeWeight)[0]
    price = subType[drinkType][drinkSubType][0]
    numToppings = random.choices([0, 1, 2, 3], weights = [0.20, 0.70, 0.07, 0.03])[0] if drinkType != "Mojito" else 0
    for x in range(numToppings):
        topping = random.choices(list(toppings.keys()), weights=toppingWeights[drinkType])[0]
        price += toppings[topping][0]
        drinkToppings.append(list(toppings.keys()).index(topping))
    iceLevel = random.choices(["Normal", "Less", "No"], weights = [0.4, 0.4, 0.2])[0] if drinkType != "Ice Blend" else "Normal"
    iceLevel += " Ice"
    sweetnessLevel = random.choices([100, 80, 50, 30, 0], weights=[0.3, 0.3, 0.1, 0.1, 0.2])[0]
    
    
        
    
    menu_id = menu_item_df[(menu_item_df["name"] == drinkSubType) & (menu_item_df["type"] == drinkType)].index[0]
    return {"menu_item_id":menu_id,"type": drinkType, "name": drinkSubType, "price": price, "toppings": drinkToppings, "ice_level":iceLevel, "sweetness":sweetnessLevel}
    
def toppings_script():
    dfCast = {}
    dfCast["name"] = list(toppings.keys())
    dfCast["price"] = [toppings[x][0] for x in toppings.keys()]
    dfCast["avaliablity"] = [100]*len(toppings.keys())
    df = pd.DataFrame(dfCast)
    df = df.rename_axis("topping_id")
    df.to_csv("topping.csv")


def customers_script(customers):
    dfCast = {}
    dfCast["name"] = list(customers.keys())
    #dfCast["Points"] = [customers[x] for x in customers.keys()]
    df = pd.DataFrame(dfCast)
    df = df.rename_axis("customer_id")
    df.to_csv("customer.csv")

def menu_item_script():
    menu_item_df = pd.DataFrame(columns=["name", "type", "price"])
    for type in subType:
        for drink in subType[type]:
            d = {"name": drink, "type": type, "price": subType[type][drink][0]}
            ser = pd.Series(data=d, index=['name', 'type', 'price'])
            menu_item_df = menu_item_df.append(ser, ignore_index=True)
    menu_item_df = menu_item_df.rename_axis("menu_item_id")
    menu_item_df.to_csv("menu_item.csv")
    return menu_item_df


def ingredients_script():
    df = pd.DataFrame()
    df["name"] = list(ingredients.keys())
    df["avaliability"] = [ingredients[x] for x in ingredients]
    df = df.rename_axis("ingredients_id")
    df.to_csv("ingredients.csv")
    return df

def menu_ingredients_mapper_script(menu_item_df, ingredients_df):
    mapper = pd.DataFrame(columns=["menu_item_id", "ingredients_id"])
    for menu_item_id, menu_item in menu_item_df.iterrows():
        # for each drink, get the subtype and name and find that location in the dictionary. Using the dictionary, get the ingredients and then find the ingredient's location in ingredients df. Finally create an entry to the new dataframe that contains the indicies
        drink_name = menu_item["name"]
        drink_type = menu_item["type"]
        drink_ingredients = subType[drink_type][drink_name][1]
        for ingredient_name in drink_ingredients:
            ingredient_id = ingredients_df[ingredients_df['name'] == ingredient_name].index[0]
            d = {"menu_item_id": menu_item_id, "ingredients_id": ingredient_id}
            ser = pd.Series(data=d, index=['menu_item_id', 'ingredients_id'])
            mapper = mapper.append(ser, ignore_index=True)
    mapper = mapper.rename_axis("menu_ingredients_mapper_id")
    mapper.to_csv("menu_ingredients_mapper.csv")
    return mapper


def orders_script(orders):
    df = pd.DataFrame(orders)
    df = df[["customer_id","employee_id","date","total_price","time"]]
    df["time"] = df["time"].apply(lambda x: x.strftime("%H:%M:%S"))
    df = df.rename_axis("order_id")
    df.to_csv("orders.csv")
    

def employee_script(a):
    df = pd.DataFrame()
    df["manager"] = [a[x] for x in a]
    df["name"] = list(a.keys())
    df = df.rename_axis("employee_id")
    df.to_csv("employee.csv")

def drinks_script(drinks):
    df = pd.DataFrame(drinks)
    df = df[["menu_item_id", "order_id","sweetness","price","ice_level"]]
    df = df.rename_axis("drink_id")
    df.to_csv("drink.csv")

def order_creator(customers, time, menu_item_df):
    customer = random.choices(list(customers.keys()))[0]
    employee = random.choices(list(employees.keys()))[0]
    drinks = []
    weights = [0.5**i for i in range(1, 101)]
    numOrder = random.choices(list(range(1, 101)), weights=weights)[0]
    for x in range(numOrder):
        drinks.append(drink_creator(menu_item_df))
    price = round(sum([x["price"] for x in drinks]),2)
    # print({"Customer": int(list(customers.keys()).index(customer)), "Employee": int(list(employees.keys()).index(employee)), "Time": time, "Drinks": drinks, "Price":price})
    return ({"customer_id": int(list(customers.keys()).index(customer)), "employee_id": int(list(employees.keys()).index(employee)), "date": time.date(), "time":time.time(), "total_price":price}, drinks)

def drink_topping_script(drink, topping):
    df = pd.DataFrame()
    df["drink_id"] = drink
    df["topping_id"] = topping
    df = df.rename_axis("drink_topping_id")
    df.to_csv("drink_topping.csv")


if __name__ == "__main__":
    menu_item_df = menu_item_script()
    ingredients_df = ingredients_script()
    menu_ingredients_mapper_script(menu_item_df, ingredients_df)
    # # print(menu_item_df)
    # print(drink_creator(menu_item_df))
    # exit()
    orders = []
    fake = Faker()
    customers = {}
    for x in range(100):
        name = fake.name()
        customers[fake.name()] = 0
    startTime = datetime.datetime(2022,1,1,9,0,0)
    # startTime = -= startTime.time
    # print(startTime)
    # order_creator(customers, startTime)
    drinks = []
    for day in range(1, 366):
        current_date = startTime + datetime.timedelta(days=day - 1)
        while True:
            holder = 400
<<<<<<< HEAD
<<<<<<< HEAD
            if current_date.hour == 12 or current_date.hour == 18:
                holder = 300
=======
>>>>>>> 6799aac7b33ef9440a8b49015512a09d2d38b46b
=======
            if current_date.hour == 12 or current_date.hour == 18:
                holder = 300
>>>>>>> main
            if current_date.month == 8 and current_date.day == 21 or current_date.month == 1 and current_date.day == 16:
                holder = 200
            current_date += datetime.timedelta(seconds=random.randint(1, holder))
            order = order_creator(customers, current_date, menu_item_df)
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
    print("called toppings")
    orders_script(orders)
    drinks_script(flattenedList)

    print(sum([x["total_price"] for x in orders]))
