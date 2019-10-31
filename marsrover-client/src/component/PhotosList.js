import React from 'react';
import Table from 'react-bootstrap/Table';
import Container from "react-bootstrap/Container";
import Image from "react-bootstrap/Image";
import moment from "moment";

const PhotosList = ({rover, date, photos}) => {

	const listItems = photos.map((photo) => {
		const imgHref = 'http://localhost:8080/api/v1/photo/' + photo.id + '?imgSrc=' + photo.img_src;

		return <tr key={photo.id}>
			<td ><a target="_blank" href={imgHref}>{photo.id}</a></td>
			<td>{photo.camera.full_name}</td>
			<td><Image thumbnail roundedCircle src={imgHref}/></td>
		</tr>;
	});

	return (
		<Container>
			<h3>{photos.length} photos retrieved for {rover} {moment(date).format("YYYY-MM-DD")} ({photos[0].sol})</h3>
			<Table striped bordered hover responsive size="sm">
				<thead>
				<tr>
					<th>Name</th>
					<th>Camera</th>
					<th>Thumbnail</th>
				</tr>
				</thead>
				<tbody>

				{listItems}
				</tbody>
			</Table>
		</Container>
	);
};

export default PhotosList;
