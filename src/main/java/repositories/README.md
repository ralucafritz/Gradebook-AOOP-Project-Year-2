## Repositories package comments:


Even though I started the project by using `entities`,`tables` and `connections` between them in the diagram of the project, it escaped my mind that I would have difficulties in actually implementing this type of project with a `DATABASE` due to the multitude of `lists`, `sets` and `maps` that I use.  
  
Because of all of this, I had to improvise, and even though it is not a `GOOD PRACTICE`, I stored the primary keys of each needed object for each `list`/`set`/`map` I have and used a field of the database as a pseudo-list in order to have a way to retrieve the data if needed. I wanted to avoid making too many repositories.

My first attempt at inserting and updating the data in the database was by implementing the `ADD`/`REMOVE` methods from each class.  
The final decision was to use a single `UPDATE` method for each repository that has one parameter with the object type of the repository we are working in.   
`example: GroupRepository - Group object`  
At the same time, in this method every field of the objects is being updated.

In the future, if the time permits, I would like to work on implementing this in a better way.