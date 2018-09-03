import React from "react";
import ReactDom from "react-dom";
import Button from '@material-ui/core/Button';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import TextField from '@material-ui/core/TextField';
import DialogContent from '@material-ui/core/DialogContent';

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {books: [], open: false, book: {id: null, name: ""}};
        this.handleClickOpen = this.handleClickOpen.bind(this);
        this.handleClose = this.handleClose.bind(this);
    }

    componentDidMount() {
        fetch('http://localhost:8080/book')
            .then(response => response.json())
            .then(data => this.setState({books: data}));
    }

    handleClickOpen(id) {
        if (id == null) {
            this.setState({book: {id: null, name: ""}, open: true});
        } else {
            fetch(`http://localhost:8080/book/${id}`)
                .then(response => response.json())
                .then(data => this.setState({book: data, open: true}));
        }
    };

    handleClose() {
        fetch('http://localhost:8080/book')
            .then(response => response.json())
            .then(data => this.setState({open: false, book: {id: null, name: ""}, books: data}));
    };

    handleSave() {
        let promise;
        if (this.state.book.id == null) {
            promise = fetch(`/book`, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(this.state.book)
            });
        } else {
            promise = fetch(`/book/${this.state.book.id}`, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(this.state.book)
            });
        }
        promise.then(() => this.handleClose());

    }

    async handleRemove(id) {
        await fetch(`/book/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedBooks = [...this.state.books].filter(b => b.id !== id);
            this.setState({books: updatedBooks});
        });
    }

    handleChange(event) {
        this.setState({book: {id: this.state.book.id, name: event.target.value}});
    }

    render() {
        return (
            <div className="App">
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Название</TableCell>
                            <TableCell/>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.books.map(book =>
                            <TableRow>
                                <TableCell>{book.id}</TableCell>
                                <TableCell>{book.name}</TableCell>
                                <TableCell>
                                    <Button color="primary"
                                            onClick={() => this.handleClickOpen(book.id)}>Редактировать</Button>
                                    <Button color="primary" onClick={() => this.handleRemove(book.id)}>Удалить</Button>
                                </TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
                <Button color="primary" onClick={() => this.handleClickOpen(null)}>Создать</Button>
                <Dialog open={this.state.open} onClose={() => this.handleClose()}>
                    <DialogTitle id="simple-dialog-title">Редактировать книгу</DialogTitle>
                    <br/>
                    <DialogContent>
                        <TextField
                            id="name"
                            label="Название"
                            margin="normal"
                            value={this.state.book.name}
                            onChange={event => this.handleChange(event)}
                        />
                        <Button onClick={() => this.handleSave()}>Сохранить</Button>
                    </DialogContent>
                </Dialog>
            </div>
        );
    }
}


ReactDom.render(<App/>, document.getElementById('react'));