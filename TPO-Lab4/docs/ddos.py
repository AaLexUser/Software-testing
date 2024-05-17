import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import subprocess
from time import sleep

jmeter_config = "~/TPO-Lab4/docs/lab4.jmx"
aggregated_data_path = "~/TPO-Lab4/docs/aggregate-graph.csv"
reports_path = "~/TPO-Lab4/docs/"

while True:
    # if exists delete reports directories
    subprocess.run(["rm", "-r", f"{reports_path}conf1"])
    subprocess.run(["rm", "-r", f"{reports_path}conf2"])
    subprocess.run(["rm", "-r", f"{reports_path}conf3"])
    subprocess.run(["rm", f"{reports_path}aggregate-graph.csv"])
    try:
        subprocess.run([f"jmeter", "-n", "-t", jmeter_config])
    except subprocess.CalledProcessError as e:
        print(f"JMeter execution failed with error code: {e.returncode}")
    df = pd.read_csv(aggregated_data_path)
    df['threadName'] = df['threadName'].str.split().str[0]
    print(df.groupby(df['threadName']).agg({'responseMessage': 'count'}))
    aggregated_data = df.groupby(df['threadName']).agg({'success': 'mean'})
    if aggregated_data.loc['conf-3'].iloc[0] != 1:
        print("Conf-3 failed: " + str(aggregated_data.loc['conf-3']))
        # sleep for 10 min
        sleep(600)
    else:
        print("Conf-3 passed: " + str(aggregated_data.loc['conf-3']))
        break
