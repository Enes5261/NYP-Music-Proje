package com.musicplayer.nypmusicproje.exceptions;

/**
 * Melodix uygulaması için tanımlanmış özel exception sınıfı
 * OOP'de exception handling, overloading ve overriding kullanımını gösterir
 */
@SuppressWarnings("unused")
public class MelodixException extends Exception {

    // Hata türünü tutar
    private ErrorType errorType;

    // Uygulamada kullanılabilecek hata türleri
    public enum ErrorType {
        FILE_NOT_FOUND,
        INVALID_USER,
        PLAYLIST_ERROR,
        SONG_ERROR,
        DATABASE_ERROR,
        UNKNOWN
    }

    // Parametresiz constructor (varsayılan hata)
    public MelodixException() {
        super("Bir hata oluştu!");
        this.errorType = ErrorType.UNKNOWN;
    }

    // Sadece hata mesajı alan constructor (Overloading)
    public MelodixException(String message) {
        super(message);
        this.errorType = ErrorType.UNKNOWN;
    }

    // Hata mesajı + hata türü alan constructor (Overloading)
    public MelodixException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    // Hata mesajı + neden (Throwable) alan constructor (Overloading)
    public MelodixException(String message, Throwable cause) {
        super(message, cause);
        this.errorType = ErrorType.UNKNOWN;
    }

    // Hata mesajı + hata türü + neden alan constructor (Overloading)
    public MelodixException(String message, ErrorType errorType, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

    // Hata türünü döndürür
    public ErrorType getErrorType() {
        return errorType;
    }

    // Exception sınıfındaki toString metodunu override eder
    @Override
    public String toString() {
        return "MelodixException [" + errorType + "]: " + getMessage();
    }

    // Hata mesajını hata türü ile birlikte döndürür (Overriding)
    @Override
    public String getMessage() {
        return "[" + errorType + "] " + super.getMessage();
    }
}
