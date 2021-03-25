from mpl_toolkits.basemap import Basemap
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from matplotlib import cm
dataset = pd.read_csv('Total crimes against women .csv')

dataset['latitude'] = dataset['Lat']
dataset['longitude'] = dataset['Long']
top_pop_cities = dataset.sort_values(by='2018',ascending=False)
top10_pop_cities=top_pop_cities.head(dataset['State'].size)
top10_pop_cities.head(dataset['State'].size)

plt.subplots(figsize=(20, 15))
map = Basemap(width=1200000,height=900000,projection='lcc',resolution='l',
                    llcrnrlon=67,llcrnrlat=5,urcrnrlon=99,urcrnrlat=37,lat_0=28,lon_0=77)
map.drawmapboundary ()
map.drawcountries ()
map.drawcoastlines ()
lg=np.array(top10_pop_cities['longitude'])
lt=np.array(top10_pop_cities['latitude'])
pt=np.array(top10_pop_cities['2018'])
nc=np.array(top10_pop_cities['State'])
x, y = map(lg, lt)
population_sizes = top10_pop_cities["2018"].apply(lambda x: int(x / 100))
plt.scatter(x, y, s=population_sizes, marker="o", c=population_sizes, cmap=cm.Dark2, alpha=0.8)
for ncs, xpt, ypt in zip(nc, x, y):
    plt.text(xpt+60000, ypt+30000, ncs, fontsize=10, fontweight='bold')
plt.title('Top Cities in India with Crime against women',fontsize=20)
plt.savefig('saved_figure.png')