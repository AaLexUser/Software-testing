import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import math

eps = 1e-5

def sec(x):
    cosRes = math.cos(x)
    if abs(cosRes) < eps:
        return math.nan
    return 1/cosRes
def csc(x):
    sinRes = math.sin(x)
    if abs(sinRes) < eps:
        return math.nan
    return 1/sinRes

def log3(x):
    return math.log(x, 3)
def log5(x):
    return math.log(x, 5)
def log10(x):
    return math.log(x, 10)

x_list = np.arange(-2*math.pi, 2*math.pi, math.pi/100)
x_list = np.append(x_list, [np.nan])
x_list = np.round(x_list, 5)
y_list = np.zeros(len(x_list))
# complex function
for i, (x, y) in enumerate(zip(x_list, y_list)):
    if( x == -10):
        print("x = 2.301")
    if x <= 0:
        if abs(x % math.pi) < 1e-5:
            y_list[i] = math.nan
        elif abs(x % math.pi - math.pi/2) < 1e-5:
            y_list[i] = math.nan
        elif abs(x % math.pi + math.pi/2) < 1e-5:
            y_list[i] = math.nan
        else:
            try: y_list[i] = ((sec(x) ** 3) / csc(x)) / (math.sin(x) + math.tan(x))
            except: print("Error at x = ", x)

    else:
        try: 
            if x == 1:
               y_list[i] = math.nan
            else:
               y_list[i] = ( (( ( ( log3(x) ** 3 ) / math.log(x) ) / (math.log(x) * log3(x))) - log5(x)) - log5(x))
        except: print("Error at x = ", x)

y_list = np.round(y_list, 5)
# formatted_list = ['{:0,.3f}'.format(num) for num in x_list]
df = pd.DataFrame({'x': x_list, 'y': y_list})
df.to_csv('myFunc.csv', index=False, na_rep='NaN')
plt.plot(x_list, y_list)
plt.xlabel('x')
plt.ylabel('y')
plt.grid()
#fix scale
plt.axis([-10, 10, -3, 10])
#minor grid
plt.minorticks_on()
plt.grid(which='minor', linestyle=':', linewidth='0.5', color='black')
plt.show()