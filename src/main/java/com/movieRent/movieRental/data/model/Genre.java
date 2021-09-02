package com.movieRent.movieRental.data.model;

public enum Genre {
    Action,Drama,Romance,Comedy,Horror;
    @Override
    public String toString() {
        switch (this){
            case Action:return "Action";
            case Drama:return "Drama";
            case Romance:return "Romance";
            case Comedy:return "Comedy";
            case Horror:return "Horror";
            default: return null;
        }

    }

}
