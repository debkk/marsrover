import React, {Component} from 'react';
import DropdownButton from 'react-bootstrap/DropdownButton';
import Dropdown from 'react-bootstrap/Dropdown';
import Button from 'react-bootstrap/Button';
import Container from "react-bootstrap/Container";
import PhotosList from "./PhotosList";
import Card from "react-bootstrap/Card";
import moment from "moment";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Tooltip from "react-bootstrap/Tooltip";

class Home extends Component {

	state = {
		error:         null,
		showResults:   false,
		photos:        [],
		selectedDate:  null,
		selectedRover: 'Curiosity'
	};

	onDateChange  = date => {
		this.setState({selectedDate: date});
	};
	onRoverSelect = rover => {
		return () => this.setState({photos: [], selectedRover: rover, showResults: false, error: null});
	};
	canGetPhotos = () => this.state.selectedDate != null;

	loadImages    = () => {
		let {selectedRover, selectedDate} = this.state;

		let requestUrl = '/api/v1/photo?earthDate=' + moment(selectedDate).format("YYYY-MM-DD") + '&rover=' + selectedRover;

		fetch(requestUrl)
			.then(res => res.json())
			.then((data) => {
				this.setState({photos: data, showResults: true, error: null});
			})
			.catch(() => {
				this.setState({error: "failed to load photos"});
			});
	};

	render() {
		return (
			<Container>
				<Card>
					<Card.Body>
						<h3>Welcome to the Mars Rover Photo List Example.</h3>

						<div>
							<OverlayTrigger
								placement='right'
								overlay={
									<Tooltip id={`tooltip-right`}>
										Try <strong>2017-02-27</strong>, <strong>2018-06-02</strong>, or <strong>2016-07-13</strong>
									</Tooltip>
								}
							>
								<label>Earth Date:&nbsp;
									<DatePicker
										selected={this.state.selectedDate}
										onChange={this.onDateChange}
										name="selectedDate"
										dateFormat="yyyy-MM-dd"
									/>

								</label>
							</OverlayTrigger>
						</div>
						<div>
							<label>
								<DropdownButton variant={"secondary"} id="dropdown-item-button" title={'Rover: ' + this.state.selectedRover}>
									<Dropdown.Item as="button" onSelect={this.onRoverSelect('Curiosity')}>Curiosity</Dropdown.Item>
									<Dropdown.Item as="button" onSelect={this.onRoverSelect('Opportunity')}>Opportunity</Dropdown.Item>
									<Dropdown.Item as="button" onSelect={this.onRoverSelect('Spirit')}>Spirit</Dropdown.Item>
								</DropdownButton></label>
							<br/>
						</div>
						<Button variant={"primary"} disabled={!this.canGetPhotos()} onClick={this.loadImages}>Get Photos!</Button>
					</Card.Body>

				</Card>
				<br/>
				{this.state.error != null ?
					<Card className="error">
						{this.state.error}
					</Card>
					: ''
				}
				{this.state.showResults ?
					<Card>
						{this.state.photos.length ? <PhotosList rover={this.state.selectedRover} date={this.state.selectedDate} photos={this.state.photos}/> : 'No photos returned'}
					</Card>
					: ''
				}


			</Container>
		);
	}


}

export default Home;
