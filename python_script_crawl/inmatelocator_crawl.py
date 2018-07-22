from bs4 import BeautifulSoup as Soup
import pandas as pd
import datetime
import requests
import time

links = pd.read_csv('jail_roster.csv')
rows = links.roster_link.str.contains('inmatelocator')
part_links = links.loc[rows]

for i in range(part_links.shape[0]):
    today = datetime.datetime.today().strftime('%Y-%m-%d')
    dic = []
    entry = part_links.iloc[i]
    state = entry['state']
    location1 = entry['location1']
    url = entry['roster_link']
    headers = {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:61.0) Gecko/20100101 Firefox/61.0'}
    r = requests.get(url, headers = headers)

    content = r.content
    dic.append({'state':state, 'location1':location1, 'date':today, 'html':content})
    f = open('%s_%s_%s.txt' %(state, location1, today), 'wb' )
    f.write(content)
    f.close()
df = pd.DataFrame.from_dict(dic)
df.to_csv('%s_%s_%s.csv' %(state, location1, today), index = False)