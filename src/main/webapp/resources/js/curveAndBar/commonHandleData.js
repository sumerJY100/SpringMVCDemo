function MillData(pipe1Data, pipe2Data, pipe3Data, pipe4Data) {
    this.pipe1Data = pipe1Data;
    this.pipe2Data = pipe2Data;
    this.pipe3Data = pipe3Data;
    this.pipe4Data = pipe4Data;

}
MillData.prototype.getPipe1DataFunction = function () {

    if (this.pipe1Data === undefined)
        return 0;
    return this.pipe1Data;
};
MillData.prototype.getPipe2DataFunction = function () {
    if (this.pipe2Data === undefined)
        return 0;
    return this.pipe2Data;
};
MillData.prototype.getPipe3DataFunction = function () {
    if (this.pipe3Data === undefined)
        return 0;
    return this.pipe3Data;
};
MillData.prototype.getPipe4DataFunction = function () {
    if (this.pipe4Data === undefined)
        return 0;
    return this.pipe4Data;
};

function getDensityDataFromMill(mill) {
    return new MillData(mill.pipe1.density, mill.pipe2.density, mill.pipe3.density, mill.pipe4.density);
}

function getVelocityDataFromMill(mill) {
    return new MillData(mill.pipe1.Velocity, mill.pipe2.Velocity, mill.pipe3.Velocity, mill.pipe4.Velocity);
}

function getMillDataFromAbsoluteToRelative(millData) {
    var avg = (millData.getPipe1DataFunction() + millData.getPipe2DataFunction() + millData.getPipe3DataFunction() + millData.getPipe4DataFunction()) / 4;
    return new MillData(millData.getPipe1DataFunction() - avg, millData.getPipe2DataFunction() - avg, millData.getPipe3DataFunction() - avg, millData.getPipe4DataFunction() - avg);
}