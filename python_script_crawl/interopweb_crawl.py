from bs4 import BeautifulSoup as Soup
import pandas as pd
import datetime
import requests
from selenium import webdriver
import time

links = pd.read_csv('jail_roster.csv')
rows = links.roster_link.str.contains('interopweb')
part_links = links.loc[rows]

#need to download chromedriver first
browser = webdriver.Chrome()


for j in range(part_links.shape[0]):

#load necessary information about jail and jail roster website
    entry = part_links.iloc[j]
    state = entry['state']
    location1 = entry['location1']
    url = entry['roster_link']
    today = datetime.datetime.today().strftime('%Y-%m-%d')

    all_info = []
    browser.get(url)
    
    #crawl each page
    time_between_page = 5 #sec
    i = 1
    while True:
        page_info = {}
        html = browser.page_source
        page_info['date'] = today
        page_info['page'] = i
        page_info['html'] = html
        
        #save html as a txt file
        f = open('%s_%s_%s_%d.txt' %(state, location1, today, i), 'w' )
        f.write(html)
        f.close()
        
        all_info.append(page_info)
        
        time.sleep(time_between_page)
        try: 
            elem = browser.find_element_by_css_selector('td[onclick*="PBN"]')
        except: 
            break

        elem.click()
        time.sleep(0.2)
        i+=1
    
    #combine each page html into a dataframe and saved as a csv
    all_info_df = pd.DataFrame.from_dict(all_info)
    all_info_df.to_csv('%s_%s_%s.csv' %(state, location1, today), index = False)

