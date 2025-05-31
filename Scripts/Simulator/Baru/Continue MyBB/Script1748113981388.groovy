import drivers.DriverMyBBParallel
import internal.GlobalVariable

appPackage = GlobalVariable.app_package
appActivity = GlobalVariable.app_activity
udid = GlobalVariable.ride_bb_udid
appiumUrl = GlobalVariable.appium_url
noReset = GlobalVariable.activeNoReset

DriverMyBBParallel.setDriverMyBB_RideBB(appPackage, appActivity, udid, appiumUrl, noReset)
