def web_crawler(url, state, location1, time_between_page, num_pages_xpath, next_page_selector, file_destination):
    today = datetime.datetime.today().strftime('%Y-%m-%d')
    browser = webdriver.Chrome()
    
    #first page
    browser.get(url)
    f = open(file_destination+'%s_%s_%s_1.txt' %(state, location1, today), 'w' )
    f.write(browser.page_source)
    f.close()
    
    time.sleep(1)
    #total num of pages
    num_pages_source =  browser.find_element_by_xpath(num_pages_xpath)
    num_pages_text = num_pages_source.text.lower()
    num_pages = int(re.findall('(?<=of ).*', num_pages_text)[0].split(' ')[0])
    
    print(num_pages)
    for i in range(1, num_pages):
        ele = browser.find_element_by_css_selector(next_page_selector)
        ele.click()
        time.sleep(time_between_page)
        f = open(file_destination+'%s_%s_%s_%d.txt' %(state, location1, today, i+1), 'w' )
        f.write(browser.page_source)
        f.close()


        i += 1

