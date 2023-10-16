# Running the Point of Sale GUI

This part of the README provides instructions on how to run the GUI.

## Instructions

1. **Within a terminal, run these commands in the main directory:**

   ```
   javac *.java
   java -cp ".;postgresql-42.2.8.jar" GUI  
   ```

   
# Database Table Initialization

This part of the README provides instructions on how to initialize database tables using the provided scripts and CSV files.


## Instructions

1. **Run the Python script to generate CSV files:**

   ```
   python script.py
   ```

   If running on your local machine, navigate to the folder containing the produced CSV files.

2. **Transfer the CSV files to the Linux server using `scp`.**

   Replace `<NETID>`, `<username>`, and `<dbname>` with the appropriate values:

   ```
   scp customer.csv drink_topping.csv drink.csv employee.csv orders.csv topping.csv <NETID>@linux2.cse.tamu.edu:/home/ugrads/<NETID[0]>/<NETID>
   ```


3. **Connect to the database using the `psql` command:**

   ```
   psql -h csce-315-db.engr.tamu.edu -U <username> -d <dbname>
   ```

4. **If tables already exist, reset them by running the following commands in the PostgreSQL shell:**

   ```
   \i clear_and_remove_tables.sql
   ```

5. **Create the tables:**

   ```
   \i create_tables.sql
   ```

5. **Grant public access to all tables:**

   ```
   \i grant_table_privileges.sql
   ```

6. **Populate the tables with data from the CSV files:**

   ```
   \i copy_data_from_csv.sql
   ```

   This step may take a few moments to complete.

Now your database tables should be initialized and populated with data from the CSV files.




