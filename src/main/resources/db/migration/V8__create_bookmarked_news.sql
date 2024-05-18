CREATE TABLE Bookmarked_News(
    user_id int,
    news_id int,
    CONSTRAINT PK_Bookmarked_News PRIMARY KEY (user_id, news_id),
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (news_id) REFERENCES News(id)
);