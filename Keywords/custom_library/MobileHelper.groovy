package custom_library

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil

public class MobileHelper {

	/**
	 * Melakukan tap berdasarkan persentase relatif ukuran layar device.
	 * @param xPercent Persentase koordinat sumbu X (0.0 - 1.0)
	 * @param yPercent Persentase koordinat sumbu Y (0.0 - 1.0)
	 */
	@Keyword
	public static void tapByPercentage(double xPercent, double yPercent) {
		int deviceWidth = Mobile.getDeviceWidth()
		int deviceHeight = Mobile.getDeviceHeight()

		int xCoord = (int) (deviceWidth * xPercent)
		int yCoord = (int) (deviceHeight * yPercent)

		Mobile.tapAtPosition(xCoord, yCoord)
	}

	/**
	 * Tap bintang rating pada halaman Complete Order secara dinamis berdasarkan 
	 * posisi dan dimensi aktual elemen RatingBar di layar.
	 * @param starNumber Nomor bintang yang dipilih (1 sampai 5)
	 */
	@Keyword
	public static void tapRatingStar(int starNumber = 5) {
		if (starNumber < 1 || starNumber > 5) {
			KeywordUtil.markFailed("Nilai starNumber berada di luar rentang valid (1 - 5).")
			return
		}

		TestObject ratingBarObj = findTestObject('Object Repository/Delivery/7. Complete Order/ratingBar')

		if (!Mobile.waitForElementPresent(ratingBarObj, 5, FailureHandling.OPTIONAL)) {
			Mobile.scrollToText("How was your trip?", FailureHandling.OPTIONAL)
		}

		// Pengambilan atribut posisi dan dimensi menggunakan keyword baku Katalon Mobile
		int left   = Mobile.getElementLeftPosition(ratingBarObj, 5)
		int top    = Mobile.getElementTopPosition(ratingBarObj, 5)
		int width  = Mobile.getElementWidth(ratingBarObj, 5)
		int height = Mobile.getElementHeight(ratingBarObj, 5)

		double starWidth = width / 5.0
		int targetX = (int) (left + (starWidth * starNumber) - (starWidth / 2.0))
		int targetY = (int) (top + (height / 2.0))

		KeywordUtil.logInfo("Proyeksi Titik Tap Bintang ${starNumber}: X=${targetX}, Y=${targetY}")
		Mobile.tapAtPosition(targetX, targetY)
	}

	/**
	 * Tap tombol 'Create Order' / 'Pesan Sekarang' pada halaman konfirmasi.
	 */
	@Keyword
	public static void tapCreateOrderButton() {
		tapByPercentage(0.71, 0.88)
	}

	/**
	 * Tap area pilihan promo pada halaman konfirmasi pesanan.
	 */
	@Keyword
	public static void tapPromoOption() {
		tapByPercentage(0.85, 0.78)
	}

	/**
	 * Tap area pilihan Trip Purpose (banner "Please enter the purpose of the trip").
	 */
	@Keyword
	public static void tapTripPurposeOption() {
		tapByPercentage(0.20, 0.73)
	}

	/**
	 * Tap opsi pilihan jadwal/schedule pengiriman.
	 */
	@Keyword
	public static void tapScheduleOption() {
		tapByPercentage(0.23, 0.88)
	}

	/**
	 * Tap opsi pilihan metode pembayaran (Payment Method).
	 */
	@Keyword
	public static void tapPaymentOption() {
		tapByPercentage(0.27, 0.78)
	}

	/**
	 * Swipe ke bawah secara otomatis sampai menemukan elemen target.
	 * @param testObject Target elemen yang dicari
	 * @param maxSwipe Batas maksimal iterasi swipe
	 * @return boolean Status keberadaan elemen
	 */
	@Keyword
	public static boolean swipeUntilElementFound(TestObject testObject, int maxSwipe = 3) {
		int currentSwipe = 0
		boolean isFound = false

		while (!isFound && currentSwipe < maxSwipe) {
			isFound = Mobile.waitForElementPresent(testObject, 2, FailureHandling.OPTIONAL)
			if (!isFound) {
				Mobile.swipe(500, 1600, 500, 400)
				Mobile.delay(1)
				currentSwipe++
			}
		}
		return isFound
	}
}