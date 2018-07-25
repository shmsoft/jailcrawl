def p2c_crawler(url, state, location1, time_between_page, select_xpath,  file_destination):
    today = datetime.datetime.today().strftime('%Y-%m-%d')
    browser = webdriver.Chrome()
    
    browser.get(url)
    drop_down = browser.find_element_by_xpath(select_xpath)
    for option in drop_down.find_elements_by_tag_name('option'):
        if option.text == 'All':
            option.click() # select() in earlier versions of
            break
    time.sleep(time_between_page)
    
    f = open(file_destination+'%s_%s_%s.txt' %(state, location1, today), 'w' )
    f.write(browser.page_source)
    f.close()
    