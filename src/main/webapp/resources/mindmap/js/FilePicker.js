/**
 * Class for interaction with the filepicker API. Provides open and save
 * from/to cloud storages.
 *
 * @constructor
 */
mindmaps.FilePicker = function (eventBus, mindmapModel) {

    // filepicker is not defined when we are offline
    if (window.filepicker) {
        var filepicker = window.filepicker;
        filepicker.setKey('P9tQ4bicRwyIe8ZUsny5');

        var mimetype = "application/json";
    }

    /**
     * Shows the open dialog and tries to open a mindmap.
     */
    this.open = function (options) {
        options = options || {};

        if (!filepicker || !navigator.onLine) {
            options.error && options.error("Cannot access cloud, it appears you are offline.");
            return;
        }

        filepicker.pick(
            {
                //mimetype: mimetype,
                extension: '.json',
                // hide: true
            },
            function (data) {
                console.log(JSON.stringify(data));

                // load mindmap
                $.ajax({
                    url: data.url,
                    success: function (data) {

                        try {
                            // convert to object first if response is a string
                            if (Object.prototype.toString.call(data) == '[object String]') {
                                data = JSON.parse(data);
                            }

                            var doc = mindmaps.Document.fromObject(data);
                        } catch (e) {
                            eventBus.publish(mindmaps.Event.NOTIFICATION_ERROR, 'File is not a valid mind map!');
                            throw new Error('Error while parsing map from cloud', e);
                        }

                        mindmapModel.setDocument(doc);

                        // execute callback
                        if (options.success) {
                            options.success(doc);
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        if (options.error) {
                            options.error("Error: Could not open mind map!");
                        }
                        throw new Error('Error while loading map from filepicker. ' + textStatus + ' ' + errorThrown);
                    }
                });

            },
            function (FPError) {
                console.log(FPError.toString());
            }
        );
    };

    /**
     * Shows the save dialog where the user can save the current mindmap. Skips
     * the dialog and saves directly when options.saveAs = true is passed and
     * a cloud storage file is currently open.
     */
    this.save = function (options) {
        options = options || {};

        if (!filepicker || !navigator.onLine) {
            options.error && options.error("Cannot access cloud, it appears you are offline.");
            return;
        }

        var data = mindmapModel.getDocument().prepareSave().serialize();

        var success = function (url) {
            console.log('saved to:', url);
            eventBus.publish(mindmaps.Event.DOCUMENT_SAVED, doc);

            if (options.success) {
                options.success();
            }
        };

        // save dialog
        options.filename = mindmapModel.getMindMap().getRoot().getCaption() + ".json";
        options.mimetype = mimetype;

        filepicker.store(
            data,
            options,
            function (blob) {
                filepicker.exportFile(
                    blob,
                    function (Blob) {
                        success(Blob.url);
                    }
                );

                console.log("Store successful:", JSON.stringify(blob));
            },
            function (FPError) {
                //  console.log(FPError.toString()); - print errors to console
            },
            function (progress) {
                console.log("Loading: " + progress + "%");
            }
        );
    }
}
;
