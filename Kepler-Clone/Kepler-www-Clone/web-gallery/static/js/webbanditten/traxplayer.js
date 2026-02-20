class TraxPlayer {
    ticker;
    playing;
    position;
    samples;
    author;
    name;
    tracks;
    volume;
    playerElement;
    time;
    timeInSeconds;
    traxLengthInSeconds;
    constructor(songUrl, sampleUrl) {
        this.volume = 0.5;
        this.samples = new Array();
        this.ready = false;
        this.position = 0;
        this.playing = false;
        this.songUrl = songUrl;
        this.sampleUrl = sampleUrl;
        this.traxLengthInSeconds = 0;
        this.timeInSeconds = 0;
        this.name = "";
        this.author = "";
        this.playerElement = document.getElementById("trax-player");
        var tracks = [];
        for (var i = 0; i < 4; i++) {
            tracks.push({
                player: new Audio(),
                timeLeft: 0,
                blocks: 0,
                sample: 0,
                playlist: []
            });
        }
        this.tracks = tracks;

        var _self = this;
        _self.SetVolume(this.volume * 100);
        var volume = _self.playerElement.getElementsByClassName("volume");
        if (volume.length > 0) {
            var slider = volume[0];

            slider.oninput = function() {
                _self.SetVolume(this.value);
            }
        }
    }

    SetVolume(volume) {
        var volumeIndicator = this.playerElement.getElementsByClassName("volume-indicator-filled");
        if (volumeIndicator.length > 0) {
            volumeIndicator[0].style.width = ((62 / 100) * volume) + "px";
        }

        this.volume = volume / 100;
        for (var i = 0; i < this.tracks.length; i++) {
            this.tracks[i].player.volume = this.volume;
        }

        this.samples.forEach(sample => {
            sample.audioObj.volume = this.volume;
        });
    }

    GetSampleUrl(sampleId) {
        return this.sampleUrl + "sound_machine_sample_" + sampleId + ".mp3";
    }

    GetTrack(sample) {
        var track = [];
        sample.split(";").forEach(sample => {
            var samplePiece = sample.split(",")[0];
            var blocks = sample.split(",")[1];
            track.push({ blocks: blocks, piece: samplePiece })
        });
        return track;
    }

    GetSampleLength(duration) {
        var result = duration * 1000;
        if (result < 2100) {
            return 1;
        }
        if (result < 4100) {
            return 2;
        }
        if (result < 6100) {
            return 3;
        }
        //if (result < 8100) {
        //    return 4;
        //}

        return 4;
        //console.log(result);
        //throw new Error("Sample is too long:");
    }

    GetDuration(sample) {
        var _self = this;
        return new Promise(function(resolve) {
            var audio = new Audio();
            audio.addEventListener('loadedmetadata', function() {
                audio.volume = _self.volume;
                resolve({
                    sampleLength: _self.GetSampleLength(audio.duration),
                    sample: sample,
                    audioObj: audio,
                    src: audio.src
                });
            });
            audio.src = _self.GetSampleUrl(sample.piece);
        });
    }

    GetUniqueSamples(tracks) {
        var flags = [];
        var output = [];

        for (var t = 0; t < tracks.length; t++) {
            for (var i = 0; i < tracks[t].length; i++) {
                if (flags[tracks[t][i].piece])
                    continue;

                flags[tracks[t][i].piece] = true;
                output.push(tracks[t][i]);
            }
        }
        return output;
    }

    async FetchSong() {
        // I will have to work with this later
        /*fetch(this.songUrl)
            .then(response => response.json())
            .then(data => console.log(data));*/
        //var song = "status=0&name=Too lost in the lido&author=Patrick&track1=317,4;408,7;0,1;410,16;413,4;406,4;410,8;412,4&track2=0,2;321,2;443,22;91,2;317,8;443,8;412,2;0,2&track3=0,3;320,2;0,7;414,4;445,4;412,2;323,2;412,4;96,2;412,2;414,4;445,7;448,1;317,4&track4=0,3;324,2;0,6;448,1;0,6;96,2;322,4;96,2;99,2;322,4;412,2;0,2;322,2;96,2;322,2;0,1;324,2;0,3";
        //var song = "status=0&name=Too lost in the lido&author=Patrick&track1=317,4;408,7;0,1;410,16;413,4;406,4;410,8;412,4:2:0,2;321,2;443,22;91,2;317,8;443,8;412,2;0,2:3:0,3;320,2;0,7;414,4;445,4;412,2;323,2;412,4;96,2;412,2;414,4;445,7;448,1;317,4:4:0,3;324,2;0,6;448,1;0,6;96,2;322,4;96,2;99,2;322,4;412,2;0,2;322,2;96,2;322,2;0,1;324,2;0,3&track2=0,2;321,2;443,22;91,2;317,8;443,8;412,2;0,2:3:0,3;320,2;0,7;414,4;445,4;412,2;323,2;412,4;96,2;412,2;414,4;445,7;448,1;317,4:4:0,3;324,2;0,6;448,1;0,6;96,2;322,4;96,2;99,2;322,4;412,2;0,2;322,2;96,2;322,2;0,1;324,2;0,3&track3=0,3;320,2;0,7;414,4;445,4;412,2;323,2;412,4;96,2;412,2;414,4;445,7;448,1;317,4:4:0,3;324,2;0,6;448,1;0,6;96,2;322,4;96,2;99,2;322,4;412,2;0,2;322,2;96,2;322,2;0,1;324,2;0,3&track4=0,3;324,2;0,6;448,1;0,6;96,2;322,4;96,2;99,2;322,4;412,2;0,2;322,2;96,2;322,2;0,1;324,2;0,3";
        //var song = "status=0&name=test&author=Patrick&track1=4,4;9,2:2:0,5;311,&track2=0,5;311,1:3:0,4;308,1;0,1:4:0,2;307,1;0,3&track3=0,4;308,1;0,1:4:0,2;307,1;0,3&track4=0,2;307,1;0,3";
        //var song = "status=0&name=test&author=Patrick&track1=4,12:2:0,5;311,1;0,6:3:0,4;308,1;0,7:4:0,2;307,1;0,9&track2=0,5;311,1;0,6:3:0,4;308,1;0,7:4:0,2;307,1;0,9&track3=0,4;308,1;0,7:4:0,2;307,1;0,9&track4=0,2;307,1;0,9";

        var song = await this.fetchUrl(this.songUrl);
        var urlSearchParams = new URLSearchParams("?" + song);

        var track1 = urlSearchParams.get("track1");
        var track2 = urlSearchParams.get("track2");
        var track3 = urlSearchParams.get("track3");
        var track4 = urlSearchParams.get("track4");
        this.title = urlSearchParams.get("name");
        this.author = urlSearchParams.get("author");
        return new Promise((resolve, reject) => {
            resolve([this.GetTrack(track1), this.GetTrack(track2), this.GetTrack(track3), this.GetTrack(track4)]);
        });
    }

    fetchUrl(url) {
        let myHeaders = new Headers();
        let options = {
            method: 'GET',
            headers: myHeaders,
            mode: 'cors'
        };

        return fetch(url, options).then(response => response.text());
    };

    OnReady() {
        this.ready = true;
        this.RemoveLoading();
        this.SetTitleAndAuthor();
    }

    RemoveLoading() {
        var loading = this.playerElement.getElementsByClassName("loading");
        if (loading.length > 0) {
            loading[0].remove();
        }
    }
    SetTitleAndAuthor() {
        var title = this.playerElement.getElementsByClassName("title");
        var author = this.playerElement.getElementsByClassName("author");
        if (title.length > 0) {
            title[0].innerHTML = this.title;
        }
        if (author.length > 0) {
            author[0].innerHTML = this.author;
        }
    }

    async Preload() { // Load all samples in song

        console.log(`SongUrl: ${
            this.songUrl
        }, sampleUrl: ${
            this.sampleUrl
        }`);
        var _self = this;

        try {
            var tracks = await this.FetchSong();

            console.log("Song loaded, loading samples");
            console.log("TRACKS");
            console.log(tracks);
            var uniqueSamples = _self.GetUniqueSamples(tracks).map(function(sample) {
                return _self.GetDuration(sample);
            });

            Promise.all(uniqueSamples).then(function(richSamples) {
                for (var i = 0; i < richSamples.length; i++) {
                    _self.samples[richSamples[i].sample.piece] = richSamples[i];
                }
                console.log("All samples loaded")

                for (var i = 0; i < tracks.length; i++) {


                    // BUILD Actual Tracks
                    var actualTrack = [];
                    for (var t = 0; t < tracks[i].length; t++) {
                        var repeat = tracks[i][t].blocks / _self.samples[tracks[i][t].piece].sampleLength;
                        for (var x = 0; x < repeat; x++) {
                            actualTrack.push(tracks[i][t].piece);
                            for (var l = 0; l < _self.samples[tracks[i][t].piece].sampleLength - 1; l++) {
                                actualTrack.push("0");
                            }

                        }
                    }
                    _self.tracks[i].playlist = actualTrack;
                }
                _self.CalculatePlaytime();
                _self.OnReady();

            });
        } catch (err) {
            console.log("Failed during preload", err);
        }

        /*this.FetchSong().then(function(tracks) {
            console.log("Song loaded, loading samples");
            console.log("TRACKS");
            console.log(tracks);
            var uniqueSamples = _self.GetUniqueSamples(tracks).map(function(sample) {
                return _self.GetDuration(sample);
            });

            Promise.all(uniqueSamples).then(function(richSamples) {
                for (var i = 0; i < richSamples.length; i++) {
                    _self.samples[richSamples[i].sample.piece] = richSamples[i];
                }
                console.log("All samples loaded")

                for (var i = 0; i < tracks.length; i++) {


                    // BUILD Actual Tracks
                    var actualTrack = [];
                    for (var t = 0; t < tracks[i].length; t++) {
                        var repeat = tracks[i][t].blocks / _self.samples[tracks[i][t].piece].sampleLength;
                        for (var x = 0; x < repeat; x++) {
                            actualTrack.push(tracks[i][t].piece);
                            for (var l = 0; l < _self.samples[tracks[i][t].piece].sampleLength - 1; l++) {
                                actualTrack.push("0");
                            }

                        }
                    }
                    _self.tracks[i].playlist = actualTrack;
                }
                _self.CalculatePlaytime();
                _self.OnReady();

            });

        }).catch(function(err) {
            console.log("Failed during preload");
        });*/

    }

    CalculatePlaytime() {
        var longestTrack = this.tracks[0].playlist;
        for (var t = 0; t < this.tracks.length; t++) {
            console.log(this.tracks[t].playlist.length)
            if (this.tracks[t].playlist.length > longestTrack.length) {
                longestTrack = this.tracks[t].playlist;
            }
        }
        var traxLengthInSeconds = longestTrack.length * 2;
        this.traxLengthInSeconds = traxLengthInSeconds;

        this.SetPlayTime();
    }

    SetPlayTime() {
        var str = this.SecondsToString(this.traxLengthInSeconds);
        var time = this.playerElement.getElementsByClassName("time");
        if (time.length > 0) {
            var totalTimeElement = document.createElement('span');
            totalTimeElement.innerHTML = "(" + str + ")";
            totalTimeElement.classList.add("length");
            time[0].innerHTML = this.SecondsToString(this.timeInSeconds);
            time[0].appendChild(totalTimeElement);
        }

        console.log(str);
    }

    SecondsToString(seconds) {
        var d = Number(seconds);
        var m = Math.floor(d % 3600 / 60);
        var s = Math.floor(d % 3600 % 60);

        var str = "0" + m + ":";
        if (s < 10) {
            str += "0";
        }
        str += "" + s;
        return str;
    }


    Tick() {
        if (this.playing) {
            for (var i = 0; i < this.tracks.length; i++) {
                this.PlayNextBeat(i);
            }
            this.position = this.position + 1;
        }

    }

    Time() {
        this.timeInSeconds = this.timeInSeconds + 1;
        if (this.timeInSeconds > this.traxLengthInSeconds) {
            this.ResetPlayer();
        } else {
            this.SetPlayTime();
        }
    }

    PlayNextBeat(track) {
        if (this.samples[this.tracks[track].playlist[this.position]]) {
            this.tracks[track].player = this.samples[this.tracks[track].playlist[this.position]].audioObj;
            this.tracks[track].timeLeft = this.samples[this.tracks[track].playlist[this.position]].sampleLength;
            this.tracks[track].blocks = this.tracks[track].playlist[this.position].blocks;
            this.tracks[track].sample = this.tracks[track].playlist[this.position];
            if (this.tracks[track].sample != 0) {
                console.log("TRACK " + track + " PLAYING: " + this.tracks[track].playlist[this.position])
                this.tracks[track].player.currentTime = 0;
                this.tracks[track].player.volume = this.volume;
                this.tracks[track].player.play();
            }
        }
    }

    Play() {
        this.playing = !this.playing;
        var playBtn = this.playerElement.getElementsByClassName("play-btn");
        var musicActivity = this.playerElement.getElementsByClassName("music-activity");
        if (this.playing) {


            if (playBtn.length > 0) {
                playBtn[0].classList.add("playing");
            }

            if (musicActivity.length > 0) {
                musicActivity[0].classList.add("playing");
            }

            this.position = 0;
            this.Tick();
            this.ticker = setInterval(function() {
                this.Tick()
            }.bind(this), 2000);
            this.time = setInterval(function() {
                this.Time()
            }.bind(this), 1000);
        } else {
            this.ResetPlayer();
        }
    }
    ResetPlayer() {
        var musicActivity = this.playerElement.getElementsByClassName("music-activity");
        var playBtn = this.playerElement.getElementsByClassName("play-btn");
        clearInterval(this.ticker);
        clearInterval(this.time);
        this.timeInSeconds = 0;
        this.SetPlayTime();
        for (var i = 0; i < this.tracks.length; i++) {
            this.tracks[i].player.pause();
        }
        this.samples.forEach(sample => {
            sample.audioObj.pause();
        });

        if (playBtn.length > 0) {
            playBtn[0].classList.remove("playing");
        }

        if (musicActivity.length > 0) {
            musicActivity[0].classList.remove("playing");
        }
    }
}