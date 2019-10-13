import React, { Component } from 'react';
import {
  Button
} from 'reactstrap';
import { connect } from 'react-redux';
import { IRootState } from 'app/shared/reducers';
export interface IlogoImageUpdateProps extends StateProps {}
export interface IlogoImageUpdateState {
    full: boolean;
    loading: boolean;
    imagePreviewUrl: any;
  }
class LogoImage extends React.Component<IlogoImageUpdateProps, IlogoImageUpdateState> {
    constructor(props) {
      super(props);
      this.state = {
        full: false,
        loading: false, 
        imagePreviewUrl: null
      };
      this.handleFileChange = this.handleFileChange.bind(this);
      this.handlePreviewClick = this.handlePreviewClick.bind(this);
      this.handleClearClick = this.handleClearClick.bind(this);
    }
    handleFileChange(event) {
      const { target } = event;
      const { files } = target;
      if (files && files[0]) {
        let reader = new FileReader();
        let file = event.target.files[0];
        reader.onloadstart = () => this.setState({ loading: true });
        reader.onloadend = () => {
          this.setState({
            imagePreviewUrl: reader.result,
            loading: false
          });
        }
        reader.readAsDataURL(file);
      }
    }
    handleClearClick() {
      this.setState({
        imagePreviewUrl: null,
        full: false
      });
    }
    handlePreviewClick() {
      const { full } = this.state;
      const data = true;
      if (!data) {
        return;
      }
      this.setState({ full: !full });
    }
    render() {
      const { imagePreviewUrl, full, loading } = this.state;
      const backgroundImage = imagePreviewUrl ? { backgroundImage: `url( ${ imagePreviewUrl })` } : null; //url(${imgData})
      const previewClasses = ['preview', full ? 'preview--fullscreen' : ''].join(' ');
      return (
        <div>
          <input
            id="logo"
            name="logo"
            type="file"
            accept="image/*"
            capture="camera"
            required
            className="imagePicker"
            onChange={ this.handleFileChange }
          />
          <div
            className={previewClasses}
            style={backgroundImage}
            onClick={this.handlePreviewClick}
          >
            {!imagePreviewUrl && !loading &&
              <label htmlFor="logo" className="pickerLabel">
                Click to capture
              </label>
            }
            {loading &&
              <span>Loading...</span>
            }
          </div>
          <Button type="button" className="btn btn-success" onClick={this.handleClearClick}> <i className="fa fa-remove"></i> Clear Image</Button>
        </div>
      );
    }
  }
  type StateProps = ReturnType<typeof mapStateToProps>;
  const mapStateToProps = (state: IRootState) => ({
    ...state
  });

  export default connect(mapStateToProps)(LogoImage);