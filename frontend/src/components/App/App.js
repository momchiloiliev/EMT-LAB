import './App.css';
import React, {Component} from "react";
import {BrowserRouter as Router, Redirect, Route} from "react-router-dom";
import LibraryService from "../../repository/LibraryService";
import Header from "../Header/header";
import Categories from "../Categories/categories";
import Authors from "../Authors/authors";
import BookAdd from "../Books/BookAdd/BookAdd";
import BookEdit from "../Books/BookEdit/BookEdit";
import Books from "../Books/BookList/BookList";

class App extends Component {
    constructor(props) {
      super(props);
      this.state ={
        authors: [],
        categories: [],
        books: [],
        selectedBook : {},
      };
    }


    render(){
      return(
          <Router>
            <Header/>
            <main>
              <div className="container">
                  <Route path={"/categories"} exact render={() =>
                      <Categories categories={this.state.categories}/>}/>

                  <Route path={"/authors"} exact render={() =>
                      <Authors authors={this.state.authors}/>}/>

                  <Route path={"/books/add"} exact render={()=>
                      <BookAdd addBook={this.addBook}
                               categories={this.state.categories}
                               authors={this.state.authors}/>}/>

                  <Route path={"/books/edit/:id"} exact render={()=>
                      <BookEdit categories={this.state.categories}
                                authors={this.state.authors}
                                onEditBook={this.editBook}
                                book={this.state.selectedBook}
                                />}/>

                  <Route path={"/books"} exact render={() =>
                      <Books books={this.state.books}
                             onEdit={this.getBook} //getBook
                             mark={this.markAsTaken}
                             onDelete={this.deleteBook}/>}/>
                  <Redirect to={"/books"}/>

                  {/*<Route path={"/*"} exact render={() =>*/}
                  {/*    <Books books={this.state.books}*/}
                  {/*           getBook={this.getBook}*/}
                  {/*           markAsTaken={this.markAsTaken}*/}
                  {/*           deleteBook={this.deleteBook}/>}/>*/}
              </div>
            </main>
          </Router>
      );
    }

    componentDidMount() {
        this.loadAuthors();
        this.loadCategories();
        this.loadBooks();
    }

    loadAuthors = () =>{
        LibraryService.fetchAuthors().then((data) => {
            this.setState({
                authors: data.data,
            });
        });
    };

    loadCategories = () => {
        LibraryService.fetchCategories().then((data) => {
            this.setState({
                categories: data.data,
            });
        });
    };

    loadBooks = () => {
        LibraryService.fetchBooks().then((data) => {
            this.setState({
                books: data.data,
            });
        });
    };

    addBook = (name, category, authorId, availableCopies) => {
        LibraryService.addBook(name, category, authorId, availableCopies).then(
            () => {
                this.loadBooks();
            }
        );
    };

    editBook = (id, name, category, authorId, availableCopies) => {
        LibraryService.editBook(id, name, category, authorId, availableCopies).then(
            () => {
                this.loadBooks();
            }
        );
    };

    getBook = (id) => {
        LibraryService.getBook(id).then((data) => {
            this.setState({
                selectedBook: data.data,
            });
        });
    };

    deleteBook = (id) => {
        LibraryService.deleteBook(id).then(() => {
            this.loadBooks();
        });
    };

    markAsTaken = (id) => {
        LibraryService.markTaken(id).then(() => {
            this.loadBooks();
        });
    };


}



export default App;
