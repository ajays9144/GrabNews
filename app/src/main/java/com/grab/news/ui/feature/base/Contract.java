package com.grab.news.ui.feature.base;

public abstract class Contract {

    /**
     * The interface Presenter.
     *
     * @param <V> the type parameter
     */
    public interface Presenter<V extends View> {
        /**
         * Attach view.
         *
         * @param v the v
         */
        void attachView(V v);

        /**
         * Detach view.
         */
        void detachView();

        /**
         * Start.
         */
        void start();
    }

    /**
     * The interface Presenter view.
     */
    public interface PresenterView extends View {
        /**
         * Clear presenter.
         */
        void clearPresenter();
    }

    /**
     * The interface View.
     */
    public interface View {
        /**
         * Show error.
         *
         * @param error the error
         */
        void showError(String error);

        void showLoading(boolean state);

        void showFailure();
    }
}