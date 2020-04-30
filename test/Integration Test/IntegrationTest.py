# CMPS 253 AUBTV App Unittesting
import sys
from selenium import webdriver
import unittest
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.action_chains import ActionChains
import requests
from selenium.webdriver.support.ui import Select

JUsername = "aubtv"
JPassword = "AuubTvtV192y"

options = webdriver.ChromeOptions()
prefs = {"profile.default_content_setting_values.notifications": 2}
options.add_experimental_option("prefs", prefs)

VidCategory = "Student Life"
VidTitle = "Selenium test video"
VidDescription = "Description for selenium test video"
VidID = "dQw4w9WgXcQ"
VidImageURL = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmedia-cdn.tripadvisor.com%2Fmedia%2Fphoto-s%2F10%2F33%2F78%2Ff7%2Faub-college-hall-1.jpg&f=1&nofb=1"

SCategory = "Campus"
STitle = "Selenium test slideshow"
SDescription = "Description for selenium test slideshow"
SImageURL = "https:\/\/johnrchildress.files.wordpress.com\/2011\/09\/aub3.jpg"

ECategory = "None"
ETitle = "Selenium test Event"
EDescription = "Description for selenium test event"
EDateYear = "2020"
EDateMonth = "04"
EDateDay = "22"
EDate = EDateYear + "-" + EDateMonth + "-" + EDateDay


class Video:
    def __init__(self, category, title, description, ID, thumbnail):
        self.category = category
        self.title = title
        self.description = description
        self.ID = ID
        self.thumbnail = thumbnail
        self.type = "Video"

    def addVideo(self):
        driver = webdriver.Chrome(chrome_options=options)
        driver.get("http://aubtv.b.mezdn.com/administrator/index.php")
        JoomlaUserName = driver.find_element_by_id("mod-login-username")
        JoomlaPassword = driver.find_element_by_id("mod-login-password")
        JoomlaUserName.send_keys(JUsername)
        JoomlaPassword.send_keys(JPassword)
        JoomlaPassword.send_keys(Keys.ENTER)
        driver.find_element_by_link_text("New Article").click()
        Title = driver.find_element_by_id("jform_title")
        Title.send_keys(self.title)

        if self.category == "Campus":
            driver.find_element_by_xpath(
                "//div[@class='chzn-container chzn-container-single']").click()
            driver.find_element_by_xpath(
                "//li[@data-option-array-index='0']").click()
        elif self.category == "Student Life":
            driver.find_element_by_xpath(
                "//div[@class='chzn-container chzn-container-single']").click()
            driver.find_element_by_xpath(
                "//li[@data-option-array-index='1']").click()
        elif self.category == "Alumni":
            driver.find_element_by_xpath(
                "//div[@class='chzn-container chzn-container-single']").click()
            driver.find_element_by_xpath(
                "//li[@data-option-array-index='3']").click()
        elif self.category == "Events":
            driver.find_element_by_xpath(
                "//div[@class='chzn-container chzn-container-single']").click()
            driver.find_element_by_xpath(
                "//li[@data-option-array-index='4']").click()
        elif self.category == "Scholarships":
            driver.find_element_by_xpath(
                "//div[@class='chzn-container chzn-container-single']").click()
            driver.find_element_by_xpath(
                "//li[@data-option-array-index='5']").click()

        driver.find_element_by_link_text("Fields").click()
        Description = driver.find_element_by_id("jform_com_fields_description")
        Description.send_keys(self.description)
        videoID = driver.find_element_by_id("jform_com_fields_youtubeid")
        videoID.send_keys(self.ID)
        imageURL = driver.find_element_by_id("jform_com_fields_imageurls")
        imageURL.send_keys(self.thumbnail)
        driver.find_element_by_xpath(
            "//label[@for='jform_com_fields_type0']").click()
        driver.find_element_by_xpath("//span[@class='icon-save']").click()
        driver.close()

    def checkVideo(self):
        ans = []
        response = requests.get(
            'http://aubtv.b.api.mezdn.com/connect/displayObject/getlist.php')
        data = response.json()
        for sub in data[self.category]:
            for key in sub:
                if sub[key] == self.title:
                    ans.append(sub[key])
                    ans.append(sub['description'])
                    ans.append(sub['youtubeId'])
                    ans.append(sub['type'])
                    ans.append(sub['imageUrls'])
        return ans

    def deleteVideo(self):
        driver = webdriver.Chrome(chrome_options=options)
        driver.get("http://aubtv.b.mezdn.com/administrator/index.php")
        JoomlaUserName = driver.find_element_by_id("mod-login-username")
        JoomlaPassword = driver.find_element_by_id("mod-login-password")
        JoomlaUserName.send_keys(JUsername)
        JoomlaPassword.send_keys(JPassword)
        JoomlaPassword.send_keys(Keys.ENTER)
        driver.find_element_by_link_text("New Article").click()
        driver.find_element_by_xpath("//span[@class='icon-cancel']").click()
        driver.find_element_by_xpath("//input[@id = 'cb0']").click()
        driver.find_element_by_xpath("//span[@class='icon-trash']").click()
        # driver.find_element_by_xpath("//button[@class='btn hasTooltip js-stools-btn-filter']").click()
        # driver.find_element_by_xpath("//div[@class='chzn-container chzn-container-single chzn-container-single-nosearch']").click()
        # driver.find_element_by_xpath("//li[@data-option-array-index='1']").click()
        # driver.find_element_by_xpath("//input[@id = 'cb0']").click()
        # driver.find_element_by_xpath("//div[@id = 'toolbar-delete']").click()
        driver.close()


class SlideShow:
    def __init__(self, category, title, description, thumbnail):
        self.category = category
        self.title = title
        self.description = description
        self.thumbnail = thumbnail
        self.type = "slideshow"

    def addSlideshow(self):
        driver = webdriver.Chrome(chrome_options=options)
        driver.get("http://aubtv.b.mezdn.com/administrator/index.php")
        JoomlaUserName = driver.find_element_by_id("mod-login-username")
        JoomlaPassword = driver.find_element_by_id("mod-login-password")
        JoomlaUserName.send_keys(JUsername)
        JoomlaPassword.send_keys(JPassword)
        JoomlaPassword.send_keys(Keys.ENTER)
        driver.find_element_by_link_text("New Article").click()
        Title = driver.find_element_by_id("jform_title")
        Title.send_keys(self.title)
        driver.find_element_by_link_text("Fields").click()
        Description = driver.find_element_by_id("jform_com_fields_description")
        Description.send_keys(self.description)
        imageURL = driver.find_element_by_id("jform_com_fields_imageurls")
        imageURL.send_keys(self.thumbnail)
        driver.find_element_by_xpath(
            "//label[@for='jform_com_fields_type1']").click()
        driver.find_element_by_xpath("//span[@class='icon-save']").click()
        driver.close()

    def checkSlideshow(self):
        ans = []
        response = requests.get(
            'http://aubtv.b.api.mezdn.com/connect/displayObject/getlist.php')
        data = response.json()
        for sub in data[self.category]:
            for key in sub:
                if sub[key] == self.title:
                    ans.append(sub[key])
                    ans.append(sub['description'])
                    ans.append(sub['type'])
                    ans.append(sub['imageUrls'])
        return ans

    def deleteSlideshow(self):
        driver = webdriver.Chrome(chrome_options=options)
        driver.get("http://aubtv.b.mezdn.com/administrator/index.php")
        JoomlaUserName = driver.find_element_by_id("mod-login-username")
        JoomlaPassword = driver.find_element_by_id("mod-login-password")
        JoomlaUserName.send_keys(JUsername)
        JoomlaPassword.send_keys(JPassword)
        JoomlaPassword.send_keys(Keys.ENTER)
        driver.find_element_by_link_text("New Article").click()
        driver.find_element_by_xpath("//span[@class='icon-cancel']").click()
        driver.find_element_by_xpath("//input[@id = 'cb0']").click()
        driver.find_element_by_xpath("//span[@class='icon-trash']").click()
        driver.close()


class Event:
    def __init__(self, category, title, description, date):
        self.category = category
        self.title = title
        self.description = description
        self.date = date
        self.type = "Event"

    def addEvent(self):
        driver = webdriver.Chrome(chrome_options=options)
        driver.get("http://aubtv.b.mezdn.com/administrator/index.php")
        JoomlaUserName = driver.find_element_by_id("mod-login-username")
        JoomlaPassword = driver.find_element_by_id("mod-login-password")
        JoomlaUserName.send_keys(JUsername)
        JoomlaPassword.send_keys(JPassword)
        JoomlaPassword.send_keys(Keys.ENTER)
        driver.find_element_by_link_text("New Article").click()
        Title = driver.find_element_by_id("jform_title")
        Title.send_keys(self.title)
        driver.find_element_by_xpath(
            "//div[@class='chzn-container chzn-container-single']").click()
        driver.find_element_by_xpath(
            "//li[@data-option-array-index='2']").click()
        driver.find_element_by_link_text("Fields").click()
        Description = driver.find_element_by_id("jform_com_fields_description")
        Description.send_keys(self.description)
        ev = driver.find_element_by_id("jform_com_fields_event_data")
        ev.send_keys(self.date)
        driver.find_element_by_xpath(
            "//label[@for='jform_com_fields_type2']").click()
        driver.find_element_by_xpath("//span[@class='icon-save']").click()
        driver.close()

    def checkEvent(self):
        ans = []
        httpg = 'http://aubtv.b.api.mezdn.com/connect/event/get.php?year=' + \
            EDateYear + "&month=" + EDateMonth + "&day=" + EDateDay
        response = requests.get(httpg)
        data = response.json()
        for sub in data["data"]:
            for key in sub:
                if sub[key] == self.title:
                    ans.append(sub[key])
                    ans.append(sub['details'])
        return ans

    def deleteEvent(self):
        driver = webdriver.Chrome(chrome_options=options)
        driver.get("http://aubtv.b.mezdn.com/administrator/index.php")
        JoomlaUserName = driver.find_element_by_id("mod-login-username")
        JoomlaPassword = driver.find_element_by_id("mod-login-password")
        JoomlaUserName.send_keys(JUsername)
        JoomlaPassword.send_keys(JPassword)
        JoomlaPassword.send_keys(Keys.ENTER)
        driver.find_element_by_link_text("New Article").click()
        driver.find_element_by_xpath("//span[@class='icon-cancel']").click()
        driver.find_element_by_xpath("//input[@id = 'cb0']").click()
        driver.find_element_by_xpath("//span[@class='icon-trash']").click()
        driver.close()


v = Video(VidCategory, VidTitle, VidDescription, VidID, VidImageURL)
s = SlideShow(SCategory, STitle, SDescription, SImageURL)
e = Event(ECategory, ETitle, EDescription, EDate)

v.addVideo()
s.addSlideshow()
e.addEvent()


class Test(unittest.TestCase):
    def testCategoryV(self):
        self.assertEqual(VidCategory, v.category)

    def testTypeV(self):
        self.assertEqual("Video", v.type)

    def testTitleV(self):
        self.assertEqual(v.checkVideo()[0], v.title)

    def testDescriptionV(self):
        self.assertEqual(v.checkVideo()[1], v.description)

    def testYoutubeIDV(self):
        self.assertEqual(v.checkVideo()[2], v.ID)

    def testCategoryS(self):
        self.assertEqual(SCategory, s.category)

    def testTypeS(self):
        self.assertEqual("slideshow", s.type)

    def testTitleS(self):
        self.assertEqual(s.checkSlideshow()[0], s.title)

    def testDescriptionS(self):
        self.assertEqual(s.checkSlideshow()[1], s.description)

    def testCategoryE(self):
        self.assertEqual("None", e.category)

    def testTypeE(self):
        self.assertEqual("Event", e.type)

    def testTitleE(self):
        self.assertEqual(e.checkEvent()[0], e.title)

    def testDescriptionE(self):
        self.assertEqual(e.checkEvent()[1], e.description)

    def testDateE(self):
        self.assertEqual(EDate, e.date)


unittest.main()

# e.deleteEvent()
# s.deleteSlideshow()
# v.deleteVideo()
